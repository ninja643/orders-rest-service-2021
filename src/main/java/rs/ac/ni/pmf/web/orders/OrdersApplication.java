package rs.ac.ni.pmf.web.orders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrdersApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(OrdersApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(final ApplicationContext context)
	{
		return args -> {
			System.out.println("Everything is initialized! Here are the beans:");

			final String[] beanDefinitionNames = context.getBeanDefinitionNames();

			for (final String beanName : beanDefinitionNames)
			{
				System.out.println("Bean found: " + beanName);
			}

			System.out.println("Total number of beans: " + beanDefinitionNames.length);
		};
	}
}
