package rs.ac.ni.pmf.web.orders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import rs.ac.ni.pmf.web.orders.model.entity.EmployeeEntity;
import rs.ac.ni.pmf.web.orders.repositories.EmployeeRepository;

@SpringBootApplication
public class OrdersApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(OrdersApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(final ApplicationContext context, final EmployeeRepository employeeRepository)
	{
		return args -> {
			showBeans(context);
			initializeData(employeeRepository);
		};
	}

	private void initializeData(final EmployeeRepository employeeRepository)
	{
		System.out.println("Initializing the data...");

		final EmployeeEntity e1 = employeeRepository.save(EmployeeEntity.builder().name("Pera").role("admin").build());
		System.out.println(e1);
		final EmployeeEntity e2 = employeeRepository.save(EmployeeEntity.builder().name("Tasa").role("user").build());
		System.out.println(e2);

		System.out.println("Data initialized");
	}

	private void showBeans(final ApplicationContext context)
	{
		//			System.out.println("Everything is initialized! Here are the beans:");
		//
		final String[] beanDefinitionNames = context.getBeanDefinitionNames();
		//
		//			for (final String beanName : beanDefinitionNames)
		//			{
		//				System.out.println("Bean found: " + beanName);
		//			}
		//
		System.out.println("Total number of beans: " + beanDefinitionNames.length);
	}
}
