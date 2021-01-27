package stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class CreateMultipleJobVacancy extends BaseClass {
	
	BaseClass b= new BaseClass();
	String Vacancy;
	

	
	@Given("^s4:User is on OrangeHRM page logged in with credentials \"(.*)\" and \"(.*)\"$")
	public void hrmLogin(String username, String password) throws Throwable {
		b.browserSetup();
		System.out.println("@Given : : "+username+" : : "+password);
		
		WebElement user = driver.findElement(By.xpath("//input[@id='txtUsername']"));
		WebElement pass = driver.findElement(By.xpath("//input[@id='txtPassword']"));
		
		user.sendKeys(username);
		pass.sendKeys(password);
		
		driver.findElement(By.cssSelector("input#btnLogin")).click();
	}

	@When("^s4:Navigate to the Recruitment page and click on the Vacancies menu$")
	public void navVacancy() throws Throwable {
		System.out.println("@When");
		
		//click Recuritment
		driver.findElement(By.cssSelector("a#menu_recruitment_viewRecruitmentModule > b")).click();
		//click vacancy
		driver.findElement(By.cssSelector("a#menu_recruitment_viewJobVacancy")).click();
	}

	@And("^s4:Click on the Add button and Fill out the necessary details and click save. \"(.*)\",\"(.*)\"$")
	public void addVacancy(String hiringmanager, String jobVacancy) throws Throwable {
		System.out.println("@And");
		driver.findElement(By.cssSelector("div.top > input#btnAdd")).click();
		
		WebElement jobTitle = driver.findElement(By.cssSelector("select#addJobVacancy_jobTitle"));
		Select dropdown = new Select(jobTitle);
		
		//jobtitle
		dropdown.selectByIndex(1);
		//vacancyname
		
		jobVacancy=jobVacancy+Math.random();
		Vacancy=jobVacancy;
		driver.findElement(By.cssSelector("input#addJobVacancy_name")).sendKeys(jobVacancy);
		//hiringManager
		driver.findElement(By.cssSelector("input#addJobVacancy_hiringManager")).sendKeys(hiringmanager);
		
		//save
		driver.findElement(By.cssSelector("input.savebutton")).click();
		
	}

	@Then("^s4:Verify the vacancy was created correctly$")
	public void verifyAddedVacancy() throws Throwable {
		System.out.println("@Then");
		
		//click vacancy
		driver.findElement(By.cssSelector("a#menu_recruitment_viewJobVacancy")).click();
		
		//jobVac
		Select jobVac = new Select(driver.findElement(By.cssSelector("select#vacancySearch_jobVacancy")));
		jobVac.selectByVisibleText(Vacancy);

		
		driver.findElement(By.cssSelector("input#btnSrch")).click();
		
		String actual = driver.findElement(By.cssSelector("td.left > a")).getText();
		
		Assert.assertEquals(Vacancy, actual);
		
	}
	
	@After
	public void close() {
		System.out.println("Create Job Vacancy");
		b.browserQuit();
	}

}
