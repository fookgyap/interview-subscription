package com.fookkeong.subscription;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class SubscriptionApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void checkParam_missingParameter() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(null, "m", "Fruda", "15/01/2021", "29/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, null, "Fruda", "15/01/2021", "29/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "m", "Fruda", null, "29/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "m", "Fruda", "15/01/2021", null))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "m", "Fruda", "20/01/2021", "18/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
	}
	
	
	@Test
	public void checkParam_subscriptionType() throws Exception{ 
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "r", "Friday", "15/01/2021", "29/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Friday", "15/01/2021", "29/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("WEEKLY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("15/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("22/01/2021"))
		.andReturn();
	}
	
	
	@Test
	public void checkParam_DayOfWeek() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "m", "Friday", "15/01/2021", "29/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "20", "15/01/2021", "29/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Fruda", "15/01/2021", "29/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Friday", "15/01/2021", "29/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("WEEKLY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("15/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("22/01/2021"))
		.andReturn();
	}
	
	
	@Test
	public void checkParam_StartDate() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Friday", "2021/01/15", "10/02/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Friday", "15/01/2021", "10/02/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("WEEKLY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("15/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("22/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[2]").value("29/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[3]").value("05/02/2021"))
		.andReturn();
	}
	
	@Test
	public void checkParam_EndDate() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Friday", "15/01/2021", "2021/02/10"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Friday", "15/01/2021", "10/02/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("WEEKLY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("15/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("22/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[2]").value("29/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[3]").value("05/02/2021"))
		.andReturn();
	}	

	@Test
	public void weeklySub_StartDateNotSameAsDayOfWeek() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Saturday", "15/01/2021", "29/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
	}
	
	@Test
	public void monthlySub_StartDateNotSameAsDayOfMonth() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "m", "14", "15/01/2021", "29/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
	}
	
	@Test
	public void check_weeklySubscriptionDuration() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Saturday", "16/01/2021", "18/04/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Friday", "15/01/2021", "10/02/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("WEEKLY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("15/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("22/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[2]").value("29/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[3]").value("05/02/2021"))
		.andReturn();
		
	}
	
	@Test
	public void check_monthlySubscriptionDuration() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "m", "16", "16/01/2021", "18/04/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "m", "15", "15/01/2021", "10/03/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("MONTHLY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("15/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("15/02/2021"))
		.andReturn();
	}

	@Test
	public void check_dailySubscriptionDuration() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "d", "null", "16/01/2021", "18/04/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isExpectationFailed())
		.andReturn();
		
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "d", "null", "15/01/2021", "19/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("DAILY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("15/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("16/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[2]").value("17/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[3]").value("18/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[4]").value("19/01/2021"))
		.andReturn();
	}
	
	
	@Test
	public void monthlySubscription_DayOfMonth_EndDate_GreaterThan_DayOfMonth_StartDate() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "m", "16", "16/01/2021", "18/02/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("MONTHLY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("16/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("16/02/2021"))
		.andReturn();
	}
	
	@Test
	public void monthlySubscription_DayOfMonth_EndDate_LessThan_DayOfMonth_StartDate() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "m", "16", "16/01/2021", "10/03/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("MONTHLY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("16/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("16/02/2021"))
		.andReturn();
	}
	
	
	@Test
	public void monthlySubscription_DayOfMonth_EndDate_SameAs_DayOfMonth_StartDate() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "m", "16", "16/01/2021", "16/03/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("MONTHLY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("16/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("16/02/2021"))
		.andReturn();
	}
	
	@Test
	public void weeklySubscription_DayOfWeek_EndDate_GreaterThan_DayOfWeek_StartDate() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Friday", "15/01/2021", "06/02/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("WEEKLY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("15/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("22/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[2]").value("29/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[3]").value("05/02/2021"))
		.andReturn();
	}

	@Test
	public void weeklySubscription_DayOfWeek_EndDate_LessThan_DayOfWeek_StartDate() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Friday", "15/01/2021", "03/02/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("WEEKLY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("15/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("22/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[2]").value("29/01/2021"))
		.andReturn();
	}
	
	
	@Test
	public void weeklySubscription_DayOfWeek_EndDate_SameAs_DayOfWeek_StartDate() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "w", "Friday", "15/01/2021", "05/02/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("15/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("22/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[2]").value("29/01/2021"))
		.andReturn();
	}
	
	@Test
	public void dailySubscription() throws Exception{
		this.mockMvc.perform(post("/subscription/subscribe")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(convertAsJsonString(new Subscription(43.0, "d", null, "16/01/2021", "20/01/2021"))).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.invAmountOutput").value(43.0))
		.andExpect(jsonPath("$.subscriptionTypeOutput").value("DAILY"))
		.andExpect(jsonPath("$.invoiceDateList.[0]").value("16/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[1]").value("17/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[2]").value("18/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[3]").value("19/01/2021"))
		.andExpect(jsonPath("$.invoiceDateList.[4]").value("20/01/2021"))
		.andReturn();
	}
	
	private String convertAsJsonString(final Object objToJson) {
	    try {
	        return new ObjectMapper().writeValueAsString(objToJson);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
