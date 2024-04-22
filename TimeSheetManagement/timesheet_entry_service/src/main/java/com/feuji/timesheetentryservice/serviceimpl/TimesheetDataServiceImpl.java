package com.feuji.timesheetentryservice.serviceimpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.feuji.timesheetentryservice.bean.AccountProjectTaskTypeBean;
import com.feuji.timesheetentryservice.bean.AccountProjectsBean;
import com.feuji.timesheetentryservice.bean.AccountTaskBean;
import com.feuji.timesheetentryservice.bean.CommonReferenceDetailsBean;
import com.feuji.timesheetentryservice.bean.EmployeeBean;
import com.feuji.timesheetentryservice.bean.ReferenceDetailsBean;
import com.feuji.timesheetentryservice.bean.WeekAndDayDataBean;
import com.feuji.timesheetentryservice.dto.EmployeeDataDto;
import com.feuji.timesheetentryservice.dto.SaveAndEditRecordsDto;
import com.feuji.timesheetentryservice.dto.TimesheetWeekDayDetailDto;
import com.feuji.timesheetentryservice.dto.WeekAndDayDto;
import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.repository.EmployeeDetailsRepo;
import com.feuji.timesheetentryservice.repository.TimesheetDayRepo;
import com.feuji.timesheetentryservice.repository.TimesheetWeekRepo;
import com.feuji.timesheetentryservice.service.TimeSheetDataService;
import com.feuji.timesheetentryservice.util.Constants;
import com.feuji.timesheetentryservice.util.EmailSender;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TimesheetDataServiceImpl implements TimeSheetDataService {

	private static Logger log = LoggerFactory.getLogger(TimesheetDayServiceImpl.class);
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	TimesheetDayRepo timesheetDayRepo;

	@Autowired
	TimesheetWeekRepo timesheetWeekRepo;

	@Autowired
	private EmployeeDetailsRepo employeeDetailsRepo;

	@Autowired
	EmailSender emailSender;

	@Override
	public List<TimesheetWeekEntity> saveOrUpdate(SaveAndEditRecordsDto saveAndEditRecordsDto, String mondayDate) {
		List<WeekAndDayDataBean> dto = saveAndEditRecordsDto.getTimesheetWeekDayDetailDto();
		Date date = convertDateStringToDate(mondayDate);
		List<TimesheetWeekEntity> savedEntities = saveAll(saveAndEditRecordsDto.getTimesheetWeekDayDetailDto(), date);

		update(saveAndEditRecordsDto.getWeekAndDayDto());
		return savedEntities;
	}

	@Override
	public List<TimesheetWeekEntity> saveAll(List<WeekAndDayDataBean> weekAndDayDataBeans, Date mondayDate) {
		List<TimesheetWeekEntity> weekEntityList = new ArrayList<>();

		LocalDate currentDate = LocalDate.now();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		int currentWeekNumber = currentDate.get(weekFields.weekOfWeekBasedYear());

		Date mondayDatee = mondayDate;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		String formattedDate = dateFormat.format(mondayDatee);

		Date monDate = convertDateStringToDate(formattedDate);

		for (WeekAndDayDataBean weekAndDayDataBean : weekAndDayDataBeans) {
			Integer employeeId = weekAndDayDataBean.getEmployeeId();
			Integer accountId = weekAndDayDataBean.getAccountId();

			List<Integer> getprojectIdOfWeek = timesheetWeekRepo.getprojectIdOfWeek(employeeId, accountId, monDate);

			if (getprojectIdOfWeek.size() == 0
					|| !getprojectIdOfWeek.contains(weekAndDayDataBean.getAccountProjectId())) {

				TimesheetWeekEntity timesheetWeekEntity = createTimesheetWeekEntity(currentWeekNumber,
						weekAndDayDataBean, mondayDatee);
				weekEntityList.add(timesheetWeekEntity);
				timesheetWeekRepo.save(timesheetWeekEntity);

				ArrayList<Date> date = new ArrayList<>(List.of(weekAndDayDataBean.getDateMon(),
						weekAndDayDataBean.getDateTue(), weekAndDayDataBean.getDateWed(),
						weekAndDayDataBean.getDateThu(), weekAndDayDataBean.getDateFri(),
						weekAndDayDataBean.getDateSat(), weekAndDayDataBean.getDateSun()));

				ArrayList<Integer> num = new ArrayList<>(List.of(weekAndDayDataBean.getHoursMon(),
						weekAndDayDataBean.getHoursTue(), weekAndDayDataBean.getHoursWed(),
						weekAndDayDataBean.getHoursThu(), weekAndDayDataBean.getHoursFri(),
						weekAndDayDataBean.getHoursSat(), weekAndDayDataBean.getHoursSun()));

				for (int j = 0; j < date.size(); j++) {
					if (num.get(j) != 0) {
						TimesheetDayEntity timeDayEntity = createTimesheetDayEntity(timesheetWeekEntity,
								weekAndDayDataBean, date.get(j), num.get(j));
						timesheetDayRepo.save(timeDayEntity);

					}
				}

			}

			else {

				Integer timesheetweekId = timesheetWeekRepo.getTimesheetweekId(weekAndDayDataBean.getAccountProjectId(),
						monDate, weekAndDayDataBean.getEmployeeId());

				TimesheetWeekEntity timesheetWeekEntity = timesheetWeekRepo.findById(timesheetweekId).get();
				weekEntityList.add(timesheetWeekEntity);

				ArrayList<Date> date = new ArrayList<>(List.of(weekAndDayDataBean.getDateMon(),
						weekAndDayDataBean.getDateTue(), weekAndDayDataBean.getDateWed(),
						weekAndDayDataBean.getDateThu(), weekAndDayDataBean.getDateFri(),
						weekAndDayDataBean.getDateSat(), weekAndDayDataBean.getDateSun()));

				ArrayList<Integer> num = new ArrayList<>(List.of(weekAndDayDataBean.getHoursMon(),
						weekAndDayDataBean.getHoursTue(), weekAndDayDataBean.getHoursWed(),
						weekAndDayDataBean.getHoursThu(), weekAndDayDataBean.getHoursFri(),
						weekAndDayDataBean.getHoursSat(), weekAndDayDataBean.getHoursSun()));

				for (int j = 0; j < date.size(); j++) {
					if (num.get(j) != 0) {
						TimesheetDayEntity timeDayEntity = createTimesheetDayEntity(timesheetWeekEntity,
								weekAndDayDataBean, date.get(j), num.get(j));
						timesheetDayRepo.save(timeDayEntity);

					}

				}

			}

		}

		return weekEntityList;

	}

	public void update(List<WeekAndDayDto> listWeekAndDayDto) {

		for (WeekAndDayDto weekAndDayDto : listWeekAndDayDto) {
			Integer timesheetWeekId = weekAndDayDto.getTimesheetWeekId();
			Integer attendanceType = weekAndDayDto.getAttendanceType();
			Integer taskId = weekAndDayDto.getTaskId();

			List<TimesheetDayEntity> listOfDayEntity = timesheetDayRepo
					.findByWeekIdAndAttendanceTypeAndTaskId(timesheetWeekId, attendanceType, taskId);

			// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, TimesheetDayEntity> existingDates = new HashMap<>();
			for (TimesheetDayEntity day : listOfDayEntity) {
				existingDates.put(formatter.format(day.getDate()), day);
			}
			this.processWeekDays(weekAndDayDto.getDateMon(), existingDates, formatter, weekAndDayDto.getHoursMon(),
					weekAndDayDto, 0);
			this.processWeekDays(weekAndDayDto.getDateTue(), existingDates, formatter, weekAndDayDto.getHoursTue(),
					weekAndDayDto, 1);
			this.processWeekDays(weekAndDayDto.getDateWed(), existingDates, formatter, weekAndDayDto.getHoursWed(),
					weekAndDayDto, 2);
			this.processWeekDays(weekAndDayDto.getDateThu(), existingDates, formatter, weekAndDayDto.getHoursThu(),
					weekAndDayDto, 3);
			this.processWeekDays(weekAndDayDto.getDateFri(), existingDates, formatter, weekAndDayDto.getHoursFri(),
					weekAndDayDto, 4);
			this.processWeekDays(weekAndDayDto.getDateSat(), existingDates, formatter, weekAndDayDto.getHoursSat(),
					weekAndDayDto, 5);
			this.processWeekDays(weekAndDayDto.getDateSun(), existingDates, formatter, weekAndDayDto.getHoursSun(),
					weekAndDayDto, 6);
		}

	}

	private void processWeekDays(Date dateValue, Map<String, TimesheetDayEntity> existingDates,
			SimpleDateFormat formatter, Integer num, WeekAndDayDto weekAndDayDto, Integer count) {

		if (dateValue != null) {
			String date = formatter.format(dateValue);
			if (existingDates.containsKey(date)) {
				TimesheetDayEntity data = existingDates.get(date);

				data.setNumberOfHours(num);

				TimesheetDayEntity dayEntity = timesheetDayRepo.save(data);
			}

		} else {

			Integer timesheetWeekId = weekAndDayDto.getTimesheetWeekId();
			// List<TimesheetDayEntity> timesheetWeekId1 =
			// timesheetDayRepo.findAllByTimesheetWeekEntityTimesheetWeekId(timesheetWeekId);
			TimesheetWeekEntity timesheetWeekEntity = timesheetWeekRepo.findById(timesheetWeekId).get();

			Date weekStartDate = timesheetWeekEntity.getWeekStartDate();

			Date weekEndDate = timesheetWeekEntity.getWeekEndDate();
			List<String> dateList = getDatesBetweenWeekStartAndEnd(weekStartDate, weekEndDate);
			if (num != 0) {

				String string = dateList.get(count);
				Date convertDateStringToDate = convertUserSpecificFormateDate(string);

				TimesheetDayEntity createTimesheetDayEntity1 = createTimesheetDayEntity1(timesheetWeekEntity,
						weekAndDayDto, convertDateStringToDate, num);

				timesheetDayRepo.save(createTimesheetDayEntity1);

			}

		}
	}

	private TimesheetWeekEntity createTimesheetWeekEntity(int currentWeekNumber, WeekAndDayDataBean weekDayData,
			Date mondayDatee) {
		TimesheetWeekEntity timesheetWeekEntity = new TimesheetWeekEntity();
		timesheetWeekEntity.setEmployeeId(weekDayData.getEmployeeId());
		timesheetWeekEntity.setAccountProjectId(weekDayData.getAccountProjectId());
		timesheetWeekEntity.setAccountId(getAccountIdFromProjectId(weekDayData.getAccountProjectId()).getAccountId());
		timesheetWeekEntity
				.setApprovedBy(getAccountIdFromProjectId(weekDayData.getAccountProjectId()).getProjectManagerId());
		timesheetWeekEntity.setWeekNumber(currentWeekNumber - 1);
		Date startDate = mondayDatee;
//		startDate.setHours(0);
//		startDate.setMinutes(0);
//		startDate.setSeconds(0);
		timesheetWeekEntity.setWeekStartDate(startDate);
		Date endDate = weekDayData.getDateSun();
		endDate.setHours(0);
		endDate.setMinutes(0);
		endDate.setSeconds(0);
		timesheetWeekEntity.setWeekEndDate(endDate);
		timesheetWeekEntity.setTimesheetStatus(Constants.TIME_SHEET_STATUS_SAVED);
		timesheetWeekEntity.setComments(weekDayData.getComments());
		timesheetWeekEntity.setCreatedBy(getEmployeeManagerByEmpId(weekDayData.getEmployeeId()).getFirstName());
		timesheetWeekEntity.setModifiedBy(getEmployeeManagerByEmpId(weekDayData.getEmployeeId()).getFirstName());
		timesheetWeekEntity.setIsDeleted((byte) 0);
		timesheetWeekEntity.setIsactive((byte) 0);
		return timesheetWeekEntity;
	}

	private TimesheetDayEntity createTimesheetDayEntity(TimesheetWeekEntity timesheetWeekEntity,
			WeekAndDayDataBean weekAndDayDataBean, Date date, int hours) {

		TimesheetDayEntity timeDayEntity = new TimesheetDayEntity();
		timeDayEntity.setTimesheetWeekEntity(timesheetWeekEntity);
		timeDayEntity.setDate(date);
		timeDayEntity.setNumberOfHours(hours);
		timeDayEntity.setAttendanceType(weekAndDayDataBean.getAttendanceType());
		timeDayEntity.setTaskId(weekAndDayDataBean.getTaskId());
		timeDayEntity.setCreatedBy(getEmployeeManagerByEmpId(weekAndDayDataBean.getEmployeeId()).getFirstName());
		timeDayEntity.setModifiedBy(getEmployeeManagerByEmpId(weekAndDayDataBean.getEmployeeId()).getFirstName());
		timeDayEntity.setIsDeleted((byte) 0);
		timeDayEntity.setIsActive((byte) 0);

		return timeDayEntity;
	}

	private TimesheetDayEntity createTimesheetDayEntity1(TimesheetWeekEntity timesheetWeekEntity,
			WeekAndDayDto weekAndDayDatoWeekAndDayDto, Date date, int hours) {

		TimesheetDayEntity timeDayEntity = new TimesheetDayEntity();
		timeDayEntity.setTimesheetWeekEntity(timesheetWeekEntity);
		date.setHours(5);
		date.setMinutes(30);
		timeDayEntity.setDate(date);
		timeDayEntity.setNumberOfHours(hours);
		timeDayEntity.setAttendanceType(weekAndDayDatoWeekAndDayDto.getAttendanceType());
		timeDayEntity.setTaskId(weekAndDayDatoWeekAndDayDto.getTaskId());
		timeDayEntity
				.setCreatedBy(getEmployeeManagerByEmpId(weekAndDayDatoWeekAndDayDto.getEmployeeId()).getFirstName());
		timeDayEntity
				.setModifiedBy(getEmployeeManagerByEmpId(weekAndDayDatoWeekAndDayDto.getEmployeeId()).getFirstName());
		timeDayEntity.setIsDeleted((byte) 0);
		timeDayEntity.setIsActive((byte) 0);

		return timeDayEntity;
	}

	public EmployeeBean getEmployeeManagerByEmpId(Integer employeeId) {
		log.info("Connecting to Employee server...");
		String url = "http://localhost:8082/api/employee/getReportingMngIdByEmpId/" + employeeId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<EmployeeBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				EmployeeBean.class);

		EmployeeBean employeeBean = responseEntity.getBody();

		return employeeBean;
	}

	public ReferenceDetailsBean getDetailsById(Integer detailsId) {
		log.info("Connecting to Employee server...");
		String url = "http://localhost:8089/api/referencedetails/getbyid/" + detailsId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<ReferenceDetailsBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				ReferenceDetailsBean.class);

		ReferenceDetailsBean referenceDetailsBean = responseEntity.getBody();

		return referenceDetailsBean;
	}

//	public AccountProjectsBean getAccountIdFromProjectId(Integer accountId) {
//		log.info("Connecting to AccountProject server...");
//		String url = "http://localhost:8083/api/accountProjects/getAccountProject/" + accountId;
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
//		ResponseEntity<AccountProjectsBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
//				AccountProjectsBean.class);
//
//		AccountProjectsBean accountProjectsBean = responseEntity.getBody();
//		return accountProjectsBean;
//
//	}
//	public AccountProjectsBean getAccountIdFromProjectId(Integer accountId) {
//	    try {
//	        log.info("Connecting to AccountProject server...");
//	        String url = "http://localhost:8083/api/accountProjects/getAccountProject/" + accountId;
//
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_JSON);
//
//	        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
//	        ResponseEntity<AccountProjectsBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
//	                AccountProjectsBean.class);
//
//	        AccountProjectsBean accountProjectsBean = responseEntity.getBody();
//	        return accountProjectsBean;
//	    } catch (HttpClientErrorException e) {
//	        log.error("HttpClientErrorException occurred while connecting to AccountProject server: {}", e.getMessage());
//	        // Handle HttpClientErrorException, e.g., log or throw custom exception
//	        return null;
//	    } catch (HttpServerErrorException e) {
//	        log.error("HttpServerErrorException occurred while connecting to AccountProject server: {}", e.getMessage());
//	        // Handle HttpServerErrorException, e.g., log or throw custom exception
//	        return null;
//	    } catch (RestClientException e) {
//	        log.error("RestClientException occurred while connecting to AccountProject server: {}", e.getMessage());
//	        // Handle RestClientException, e.g., log or throw custom exception
//	        return null;
//	    } catch (Exception e) {
//	        log.error("An unexpected error occurred while connecting to AccountProject server: {}", e.getMessage());
//	        // Handle any other unexpected exception, e.g., log or throw custom exception
//	        return null;
//	    }
//	}
//

	public AccountProjectsBean getAccountIdFromProjectId(Integer accountId) {
		try {
			log.info("Connecting to AccountProject server...");
			String url = "http://localhost:8083/api/accountProjects/getAccountProject/" + accountId;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);
			ResponseEntity<AccountProjectsBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
					AccountProjectsBean.class);

			AccountProjectsBean accountProjectsBean = responseEntity.getBody();

			if (accountProjectsBean != null) {
				log.info("Received AccountProjectsBean from AccountProject server: {}", accountProjectsBean);
				return accountProjectsBean;
			} else {
				log.error("Received null response from AccountProject server");
				// Handle the case where the response is null, e.g., throw custom exception or
				// return default value
				return null;
			}
		} catch (HttpClientErrorException e) {
			log.error("HttpClientErrorException occurred while connecting to AccountProject server: {}",
					e.getMessage());
			// Handle HttpClientErrorException, e.g., log or throw custom exception
			return null;
		} catch (HttpServerErrorException e) {
			log.error("HttpServerErrorException occurred while connecting to AccountProject server: {}",
					e.getMessage());
			// Handle HttpServerErrorException, e.g., log or throw custom exception
			return null;
		} catch (RestClientException e) {
			log.error("RestClientException occurred while connecting to AccountProject server: {}", e.getMessage());
			// Handle RestClientException, e.g., log or throw custom exception
			return null;
		} catch (Exception e) {
			log.error("An unexpected error occurred while connecting to AccountProject server: {}", e.getMessage());
			// Handle any other unexpected exception, e.g., log or throw custom exception
			return null;
		}
	}

	public AccountProjectTaskTypeBean getAccountTaskType(Integer taskTypeId) {
		log.info("Connecting to other server...");
		String url = "http://localhost:8083/api/accountProjectTaskType/" + taskTypeId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<AccountProjectTaskTypeBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				httpEntity, AccountProjectTaskTypeBean.class);

		AccountProjectTaskTypeBean accountProjectsTaskTypeBean = responseEntity.getBody();
		return accountProjectsTaskTypeBean;

	}

	public AccountTaskBean getAccountTask(Integer taskId) {
		log.info("Connecting to other server...");
		String url = "http://localhost:8083/api/accountProjects/getbyid/" + taskId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<AccountTaskBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				AccountTaskBean.class);

		AccountTaskBean accountTaskBean = responseEntity.getBody();
		return accountTaskBean;

	}

	public CommonReferenceDetailsBean getAttendanceType(Integer attendanceTypeId) {
		try {
			log.info("Connecting to CommonRefarance  server...");
			String url = "http://localhost:8089/api/referencedetails/getbyid/" + attendanceTypeId;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);
			ResponseEntity<CommonReferenceDetailsBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					httpEntity, CommonReferenceDetailsBean.class);

			CommonReferenceDetailsBean commonDetailsBean = responseEntity.getBody();
			return commonDetailsBean;
		} catch (Exception e) {

			log.error("Client error while connecting to the server: {}", e.getMessage(), e);

			return null;
		}

	}

	public List<WeekAndDayDto> fetchAllWeekDayRecordsById(Integer accountId, Integer employeeId, String weekStartDate,
			String weekEndDate) {
		log.info("fetching All Week Day Data" + employeeId + accountId);

		Date startDate = convertDateStringToDate(weekStartDate);
		Date endDate = convertDateStringToDate(weekEndDate);

		try {

			List<TimesheetWeekDayDetailDto> timesheetWeekDayDetails = timesheetWeekRepo
					.findTimesheetDetailsByDateRange(accountId, employeeId, startDate, endDate);

			List<WeekAndDayDto> weekAndDayDtoList = new ArrayList<>();
			Map<String, WeekAndDayDto> weekDayMap = new TreeMap<>();

			for (TimesheetWeekDayDetailDto timesheetWeekDayDetailDto : timesheetWeekDayDetails) {

				String key = "" + timesheetWeekDayDetailDto.getEmployeeId()
						+ timesheetWeekDayDetailDto.getAccountProjectId() + timesheetWeekDayDetailDto.getTaskTypeId()
						+ timesheetWeekDayDetailDto.getTaskId() + timesheetWeekDayDetailDto.getAttendanceType();

				if (weekDayMap.containsKey(key)) {
					Timestamp date = timesheetWeekDayDetailDto.getDate();
					String dayOfWeek = getDayOfWeek(date);

					WeekAndDayDto weekAndDayDto = weekDayMap.get(key);
					if (dayOfWeek.equalsIgnoreCase("MONDAY")) {
						weekAndDayDto.setHoursMon(timesheetWeekDayDetailDto.getNumberOfHours());
						weekAndDayDto.setDateMon(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("TUESDAY")) {
						weekAndDayDto.setHoursTue(timesheetWeekDayDetailDto.getNumberOfHours());
						weekAndDayDto.setDateTue(timesheetWeekDayDetailDto.getDate());
					}

					if (dayOfWeek.equalsIgnoreCase("WEDNESDAY")) {
						weekAndDayDto.setHoursWed(timesheetWeekDayDetailDto.getNumberOfHours());
						weekAndDayDto.setDateWed(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("THURSDAY")) {
						weekAndDayDto.setHoursThu(timesheetWeekDayDetailDto.getNumberOfHours());
						weekAndDayDto.setDateThu(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("FRIDAY")) {
						weekAndDayDto.setHoursFri(timesheetWeekDayDetailDto.getNumberOfHours());
						weekAndDayDto.setDateFri(timesheetWeekDayDetailDto.getDate());
					}

				} else {

					Timestamp date = timesheetWeekDayDetailDto.getDate();
					String dayOfWeek = getDayOfWeek(date);

					WeekAndDayDto timesheetWeekDayDto = WeekAndDayDto.builder()
							.timesheetWeekId(timesheetWeekDayDetailDto.getTimesheetWeekId())
							.employeeId(timesheetWeekDayDetailDto.getEmployeeId())
							.accountId(timesheetWeekDayDetailDto.getAccountId())
							.accountProjectId(timesheetWeekDayDetailDto.getAccountProjectId())
							.projectName(getAccountIdFromProjectId(timesheetWeekDayDetailDto.getAccountProjectId())
									.getProjectName())
							.taskId(timesheetWeekDayDetailDto.getTaskId())
							.taskName(getAccountTask(timesheetWeekDayDetailDto.getTaskId()).getTask())
							.taskTypeId(timesheetWeekDayDetailDto.getTaskTypeId())
							.taskTypeName(getAccountTaskType(timesheetWeekDayDetailDto.getTaskTypeId()).getTaskType())
							.attendanceTypeName(getAttendanceType(timesheetWeekDayDetailDto.getAttendanceType())
									.getReferenceDetailValue())
							.attendanceType(timesheetWeekDayDetailDto.getAttendanceType())
							.timesheetStatus(getDetailsById(timesheetWeekDayDetailDto.getTimesheetStatus())
									.getReferenceDetailId())
							.timesheetStatusname(getDetailsById(timesheetWeekDayDetailDto.getTimesheetStatus())
									.getReferenceDetailValue())
							.weekStartDate(timesheetWeekDayDetailDto.getWeekStartDate()).build();
					if (dayOfWeek.equalsIgnoreCase("MONDAY")) {
						timesheetWeekDayDto.setHoursMon(timesheetWeekDayDetailDto.getNumberOfHours());
						timesheetWeekDayDto.setDateMon(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("TUESDAY")) {
						timesheetWeekDayDto.setHoursTue(timesheetWeekDayDetailDto.getNumberOfHours());
						timesheetWeekDayDto.setDateTue(timesheetWeekDayDetailDto.getDate());
					}

					if (dayOfWeek.equalsIgnoreCase("WEDNESDAY")) {
						timesheetWeekDayDto.setHoursWed(timesheetWeekDayDetailDto.getNumberOfHours());
						timesheetWeekDayDto.setDateWed(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("THURSDAY")) {
						timesheetWeekDayDto.setHoursThu(timesheetWeekDayDetailDto.getNumberOfHours());
						timesheetWeekDayDto.setDateThu(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("FRIDAY")) {
						timesheetWeekDayDto.setHoursFri(timesheetWeekDayDetailDto.getNumberOfHours());
						timesheetWeekDayDto.setDateFri(timesheetWeekDayDetailDto.getDate());
					}

					weekDayMap.put(key, timesheetWeekDayDto);
					weekAndDayDtoList.add(timesheetWeekDayDto);
				}

			}

			return weekAndDayDtoList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String getDayOfWeek(Timestamp timestamp) {
		try {

			Instant instant = timestamp.toInstant();

			DayOfWeek dayOfWeek = instant.atZone(java.time.ZoneOffset.UTC).getDayOfWeek();

			return dayOfWeek.name();
		} catch (Exception e) {
			System.err.println("Error getting day of the week: " + e.getMessage());
			return null;
		}
	}

	private static List<LocalDate> getWeekDates(LocalDate weekStartDate) {
		List<LocalDate> weekDates = new ArrayList<>();

		try {
			for (int i = 0; i < DayOfWeek.values().length; i++) {
				LocalDate currentDate = weekStartDate.plusDays(i);
				weekDates.add(currentDate);
			}
		} catch (DateTimeException e) {

			System.err.println("Error occurred while calculating week dates: " + e.getMessage());

			throw new RuntimeException("Failed to calculate week dates", e);
		}

		return weekDates;
	}

	@Override
	public List<TimesheetDayEntity> deleteDayRecord(WeekAndDayDto weekAndDayDto) {

		List<TimesheetDayEntity> timesheetDayEntityList = new ArrayList<>();
		Integer timesheetWeekId = weekAndDayDto.getTimesheetWeekId();

		List<TimesheetDayEntity> listOfTimesheetDayEntity = timesheetDayRepo
				.findAllByTimesheetWeekEntityTimesheetWeekId(timesheetWeekId);

		for (TimesheetDayEntity timesheetDayEntity : listOfTimesheetDayEntity) {

			if (weekAndDayDto.getTaskId().equals(timesheetDayEntity.getTaskId())
					&& weekAndDayDto.getAttendanceType().equals(timesheetDayEntity.getAttendanceType())) {

				timesheetDayEntity.setIsDeleted((byte) 1);

				timesheetDayRepo.save(timesheetDayEntity);

			}
			List<TimesheetDayEntity> listOfTimesheetDayEntity2 = timesheetDayRepo
					.findAllByTimesheetWeekEntityTimesheetWeekId(timesheetWeekId);
			if (listOfTimesheetDayEntity2.size() <= 0) {

				TimesheetWeekEntity timesheetWeekEntity = timesheetWeekRepo.findById(timesheetWeekId).orElseThrow();
				timesheetWeekEntity.setIsDeleted((byte) 1);
				timesheetWeekRepo.save(timesheetWeekEntity);

			}

		}

		return listOfTimesheetDayEntity;
	}

	@Override
	public List<TimesheetWeekEntity> submittingTimesheet(String weekStartDate, Integer timesheetStatus,
			Integer accountId, Integer employeeId) {

		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date convertedWeekStartDate = dateFormat.parse(weekStartDate);

			List<TimesheetWeekEntity> findByWeekStartDate = timesheetWeekRepo
					.findByWeekStartDateAndAccountIdAndEmployeeId(convertedWeekStartDate, accountId, employeeId);

			for (TimesheetWeekEntity timesheetWeekEntity : findByWeekStartDate) {
				timesheetWeekEntity.setTimesheetStatus(timesheetStatus);
				timesheetWeekRepo.save(timesheetWeekEntity);
			}

			return findByWeekStartDate;
		} catch (ParseException e) {

			e.printStackTrace();
			return null;
		}

	}

	public static Date convertDateStringToDate(String dateString) {
		try {

			LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MMM-yyyy"));

			Date date = java.sql.Date.valueOf(localDate);
			return date;

		} catch (DateTimeParseException e) {

			return null;
		}

	}

	private static Date convertUserSpecificFormateDate(String dateStr) {

		// Define input and output date formats
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			// Parse the input date string to java.util.Date
			Date date = inputFormat.parse(dateStr);

			// Format java.util.Date to the desired string format
			String formattedDate = outputFormat.format(date);

			d = outputFormat.parse(formattedDate);

		} catch (ParseException e) {
			// Handle parse exception
			e.printStackTrace();
		}
		return d;
	}

	public static List<String> getDatesBetweenWeekStartAndEnd(Date weekStartDate, Date weekEndDate) {
		LocalDate localStartDate = convertToLocalDate(weekStartDate);
		LocalDate localEndDate = convertToLocalDate(weekEndDate);

		long daysBetween = ChronoUnit.DAYS.between(localStartDate, localEndDate);

		List<String> dateStringList = new ArrayList<>();
		dateStringList.add(convertToString(weekStartDate));
		for (int i = 1; i < daysBetween; i++) {
			LocalDate date = localStartDate.plusDays(i);
			dateStringList.add(date.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
		}
		dateStringList.add(convertToString(weekEndDate));

		return dateStringList;
	}

	public static LocalDate convertToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static String convertToString(Date date) {
		return new java.text.SimpleDateFormat("dd-MMM-yyyy").format(date);
	}

	public List<EmployeeDataDto> getEmployeeDetailsByIdAndAccountId(Integer accountId, Integer employeeId) {

		List<Object[]> list = employeeDetailsRepo.getAccountManagerDetails(accountId, employeeId);
		List<EmployeeDataDto> empList = new ArrayList<>();
		for (Object[] o : list) {
			System.out.println(o.toString());
			EmployeeDataDto emp = new EmployeeDataDto();
			emp.setFirstName((String) o[0]);
			emp.setLastName((String) o[1]);
			emp.setEmail((String) o[2]);
			empList.add(emp);

		}
		return empList;
	}

	public List<EmployeeDataDto> getReportingManagerByIdAndAccountId(Integer accountId, Integer employeeId) {

		List<Object[]> list = employeeDetailsRepo.getReportingManagerDetails(accountId, employeeId);
		List<EmployeeDataDto> empList = new ArrayList<>();
		for (Object[] o : list) {

			EmployeeDataDto emp = new EmployeeDataDto();
			emp.setFirstName((String) o[0]);
			emp.setLastName((String) o[1]);
			emp.setEmail((String) o[2]);
			empList.add(emp);
		}
		return empList;
	}

	public void processPendingTimesheetsBySubmittedStatus() throws Exception {

		List<TimesheetWeekEntity> weekList = timesheetWeekRepo
				.findByTimesheetStatus(Constants.TIME_SHEET_STATUS_SUBMITTED);

		LocalDate currentDate = LocalDate.now();
		for (TimesheetWeekEntity entity : weekList) {

			Instant instant = Instant.ofEpochMilli(entity.getModifiedOn().getTime());

			ZoneId zoneId = ZoneId.of("UTC"); // Adjust to your desired time zone
			LocalDate modifiedDate = instant.atZone(zoneId).toLocalDate();
			modifiedDate = modifiedDate.plusDays(Constants.TIME_SHEET_GRACE_NUMBER_OF_DAYS);
			if (modifiedDate.compareTo(currentDate) < 0) {

				List<EmployeeDataDto> empList = this.getReportingManagerByIdAndAccountId(entity.getAccountId(),
						entity.getEmployeeId());
				if (!empList.isEmpty()) {
					for (EmployeeDataDto emp : empList) {

						emailSender.sendSimpleEmail(emp.getEmail(),
								"Request for Timesheet Approval from Reporting Manager",
								this.composeBody(emp, entity.getWeekStartDate(), entity.getWeekEndDate()));
					}
				}
			}
		}

	}

	private String composeBody(EmployeeDataDto emp, Date startDate, Date endDate) throws Exception {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
		String startDateLocal = formatter.format(startDate.toInstant());
		String endDateLocal = formatter.format(endDate.toInstant());

		String emailBody = "Dear " + emp.getFirstName() + " " + emp.getLastName() + ",\n\n"
				+ "The Project Manager not yet approve the timesheet for the period from: " + startDateLocal.toString()
				+ " to: " + endDateLocal.toString() + " Please review and approve it accordingly.";

		return emailBody;
	}

}
