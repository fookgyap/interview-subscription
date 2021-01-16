package com.fookkeong.subscription;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.apache.commons.lang3.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
	
	@Autowired
	SubscriptionResult subscriptionResult;
	
	@PostMapping("/subscribe")
	public SubscriptionResult subscribe(@RequestBody Subscription subscription) {
		
		validateFields(subscription);
		subscriptionResult.setInvAmountOutput(subscription.getAmount());


		Optional<SubscriptionType> subTypeOpt = Arrays.asList(SubscriptionType.values()).stream().filter(x->x.getSubCode().equals(subscription.getSubscriptionType())).findAny();
		SubscriptionType  subTypeObj = subTypeOpt.get();

		subscriptionResult.setSubscriptionTypeOutput(subTypeObj.name());
		subscriptionResult.setInvoiceDateList(generateInvoiceDates(subscription));
		
		return subscriptionResult;
		
	}
	
	public void validateFields(Subscription subscription) {
		
		Double amount = subscription.getAmount();
		String dayOfWeek = subscription.getDayOfWeek();
		String subscriptionType = subscription.getSubscriptionType();
		String pStartDate = subscription.getStartDate();
		String pEndDate = subscription.getEndDate();
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
		
		if(null == amount || null == subscriptionType || null == pStartDate || null == pEndDate) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Missing parameter");
		}
		
		try {
			LocalDate startDate = LocalDate.parse(pStartDate, formatter);
			LocalDate endDate = LocalDate.parse(pEndDate, formatter);
			
			
			if(startDate.isAfter(endDate)) {
				throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Invalid Parameter. Start date cannot be after end date");
			}
			
			if(SubscriptionType.WEEKLY.getSubCode().equals(subscriptionType)) {
				
				if(null == dayOfWeek || StringUtils.isNumeric(dayOfWeek)){
					throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Invalid Parameter type: dayOfWeek. Expecting day of week. e.g. Monday");
				}
				
				Optional<DayOfWeek> validDayWeek = Arrays.asList(DayOfWeek.values()).stream().filter(dayOfWeekObj -> dayOfWeekObj.name().equalsIgnoreCase(dayOfWeek)).findAny();
				if(!validDayWeek.isPresent()) {
					throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Invalid Parameter: dayOfWeek");
				}
				DayOfWeek dayWeek = (DayOfWeek)validDayWeek.get();
				if(!startDate.getDayOfWeek().equals(dayWeek)) {
					throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Invalid Parameter: dayOfWeek is not inline with the day of start date");
				}
				
			}else if(SubscriptionType.MONTHLY.getSubCode().equals(subscriptionType)) {
				if(null == dayOfWeek || !StringUtils.isNumeric(dayOfWeek)){
					throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Invalid Parameter: dayOfWeek. Expecting numeric value. e.g. 20");
				}
				
				if(startDate.getDayOfMonth() != Integer.parseInt(dayOfWeek)) {
					throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Invalid Parameter: dayOfWeek is not inline with the day of start date");
				}
				
			}else if(!SubscriptionType.DAILY.getSubCode().equals(subscriptionType)) {
				throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Invalid Parameter: subscriptionType. Available input: d, w, m");
			}
			
			Long subscriptionDuration = ChronoUnit.MONTHS.between(startDate, endDate) + 1;
			if(subscriptionDuration > 3) {
				throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Duration of subscription cannot exceed three months.");
			}
		}catch(DateTimeParseException e) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Invalid format for start date/end date parameter. Allowed format: dd/MM/yyyy");
		}
	}
	
	public List<String> generateInvoiceDates(Subscription subscription) {
		List<String> invoiceDateList = new ArrayList<String>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
		
		String subscriptionType = subscription.getSubscriptionType();
		String pStartDate = subscription.getStartDate();
		String pEndDate = subscription.getEndDate();

		LocalDate startDate = LocalDate.parse(pStartDate, formatter);
		LocalDate endDate = LocalDate.parse(pEndDate, formatter);

		if(SubscriptionType.WEEKLY.getSubCode().equals(subscriptionType)) {
			Long weeksBetween = ChronoUnit.WEEKS.between(startDate, endDate) + 1;
			invoiceDateList = IntStream.iterate(0, count -> count + 1)
					.limit(weeksBetween)
					.mapToObj(counter -> {
						LocalDate newStartDate = startDate.plusWeeks(counter);
						if(endDate.equals(newStartDate)) {
							return null;
						}
						return formatter.format(newStartDate);
					})
					.filter(x -> null != x)
					.collect(Collectors.toList());


		}else if(SubscriptionType.MONTHLY.getSubCode().equals(subscriptionType)) {
			Long monthsBetween = ChronoUnit.MONTHS.between(startDate, endDate) + 1;
			invoiceDateList = IntStream.iterate(0, count -> count + 1)
					.limit(monthsBetween)
					.mapToObj(counter -> {
						LocalDate newStartDate = startDate.plusMonths(counter);
						if(endDate.equals(newStartDate)) {
							return null;
						}
						return formatter.format(newStartDate);
					})
					.filter(x -> null != x)
					.collect(Collectors.toList());

		}else {
			Long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
			invoiceDateList = IntStream.iterate(0, count -> count + 1)
					.limit(daysBetween)
					.mapToObj(counter -> {
						LocalDate newStartDate = startDate.plusDays(counter);
						return formatter.format(newStartDate);	
					})
					.collect(Collectors.toList());
		}
		
		
		return invoiceDateList;
	}
}
