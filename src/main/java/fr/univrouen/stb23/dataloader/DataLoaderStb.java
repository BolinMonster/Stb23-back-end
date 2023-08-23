package fr.univrouen.stb23.dataloader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import fr.univrouen.stb23.model.Client;
import fr.univrouen.stb23.model.Stb;
import fr.univrouen.stb23.service.StbService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class DataLoaderStb {

	@Autowired
	private StbService stbService;

	@PostConstruct
	public void loadData() {
		final Faker faker = new Faker(new Locale("fr"));
		List<Stb> stbs = new ArrayList<Stb>();
		final int numberOfStbs = 10;
		Stb stb;
		for (int i = 0; i < numberOfStbs; i++) {
			stb = new Stb();
			stb.setTitle(faker.app().name());
			stb.setVersion(faker.number().randomDouble(2, 1, 10));
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -20);
			final Date from = calendar.getTime();
			calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -10);
			final Date to = calendar.getTime();
			final Date dateValidation = faker.date().between(from, to);
			stb.setValidationDate(dateValidation);
			final String paragraph = faker.lorem().paragraph();
			final int length = paragraph.length();
			final int endIndex = (int) (length * 0.9);
			final String description = paragraph.substring(0, endIndex);
			stb.setDescription(description);
			// stb.setMembers(Arrays.asList(new Member(), new Member()));
		    // stb.setFeatures(Arrays.asList(new Feature(), new Feature()));
			stbs.add(stb);
		}
		stbService.addStbs(stbs);
	}

	@PreDestroy
	public void removeData() {
		stbService.removeAll();
	}

}
