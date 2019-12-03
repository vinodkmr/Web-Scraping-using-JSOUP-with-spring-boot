package com.vinodkmr.tech;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.websocket.server.PathParam;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vinodkmr.tech.constants.Constants;
import com.vinodkmr.tech.model.TechArticle;

@Service
@Controller
public class TechNewsLookUpService {

	List<TechArticle> techArticleList = new ArrayList();

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	@Value("#{'${HackerNoonURL}'.split(',')}") 
	private List<String> HackerNoonURLList;	


	@Scheduled(cron = "1 1 * ? * *")
	public void scheduleTaskWithFixedRate() throws ParseException{
		hackerNoonLookUp();
		inshortsLookUp();		
		Collections.sort(techArticleList, (t1,t2) -> t2.getPublishedDate().compareTo(t1.getPublishedDate()));
		limitArticleListSize();
	}

	private void hackerNoonLookUp() {
		System.out.println("The time is now {}"+ dateFormat.format(new Date()));
		for(String url : HackerNoonURLList) {
			System.out.println("URl Hitting "+url);
			Document doc = null; 
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException ioe) { 
				ioe.printStackTrace(); 
			}


			Elements elements = doc.getElementsByClass("story-card"); 
			for(Element element : elements) {
				String articleLink =  element.select("div.title").first().children().get(0).attr("abs:href");
				String header =	element.select("div.title").first().children().get(0).ownText();
				if(techArticleList.size() > 0) {
					if(techArticleList.stream().
							filter(article -> article.getHeader().
									equalsIgnoreCase(header)).count() > 0) {
						String nextElementHeader = element.nextElementSibling().getElementsByAttribute("href").attr("abs:href");
						if(techArticleList.stream().
								filter(article -> article.getHeader()
										.equalsIgnoreCase(nextElementHeader)).count()>0) {
							System.out.println("Duplicate "+nextElementHeader);
							break;
						}else {
							continue;
						}
					}
				}

				String attr = element.getElementsByClass("img").attr("style"); 
				String imageLink = attr.substring(attr.indexOf("('")+2  , attr.indexOf("')"));
				imageLink = imageLink.contains("http")? imageLink.substring(imageLink.indexOf("http")):
					"https://hackernoon.com"+imageLink;
				String publishedDate = element.getElementsByClass("published").first().ownText();
				System.out.println("published "+publishedDate);

				int day = Integer.parseInt(publishedDate.split(" ")[1]);
				int month = Month.valueOf(publishedDate.split(" ")[0].toUpperCase()).getValue()-1;

				Calendar calendar = setTime(publishedDate, day, month);


				System.out.println(header);
				System.out.println(imageLink);
				techArticleList.add(new TechArticle(header, imageLink, articleLink, calendar.getTime()));

			}


		}


	}

	private Calendar setTime(String publishedDate, int day, int month) {
		Calendar calendar = Calendar.getInstance();

		if(month >= calendar.get(Calendar.MONTH)+1) {
			if(day > calendar.get(Calendar.DATE))
				calendar.set(Calendar.YEAR,-1);
		}

		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE, day);
		return calendar;
	}


	private void inshortsLookUp() throws ParseException {

		Document doc = null; 
		try {
			doc = Jsoup.connect("https://inshorts.com/en/read/technology").get();
		} catch (IOException ioe) { 
			ioe.printStackTrace(); 
		}


		Elements elements = doc.selectFirst("div.card-stack").children();
		for(Element element : elements) {
			Element newsCardElement = element.selectFirst("div.news-card.z-depth-1");
			if(newsCardElement != null) {

				String header = newsCardElement.select("[itemprop='headline']").first().ownText();
				String backgroundImgLink = newsCardElement.selectFirst("div.news-card-image").attr("style");
				System.out.println("article header "+header);
				String imageLink = backgroundImgLink.substring(backgroundImgLink.indexOf("http"), 
						backgroundImgLink.indexOf("?"));


				if(techArticleList.size() > 0) {
					if(techArticleList.stream().
							filter(article -> article.getHeader().
									equalsIgnoreCase(header)).count() > 0) {
						String nextElementHeader = 
								element.nextElementSibling() != null?
										element.nextElementSibling().select("[itemprop='headline']").first().ownText():
											"";
										if(techArticleList.stream().
												filter(article -> article.getHeader()
														.equalsIgnoreCase(nextElementHeader)).count()>0) {
											System.out.println("Duplicate "+nextElementHeader);
											break;
										}else {
											continue;
										}
					}
				}

				String articleLink = null;

				String publishedDate = newsCardElement.selectFirst("span.date").ownText();
				if(newsCardElement.selectFirst("a.source") != null)
					articleLink = newsCardElement.selectFirst("a.source").absUrl("href");

				int articleDay = Integer.parseInt(publishedDate.split(" ")[0]);
				int articleMonth =  new SimpleDateFormat("MMM").parse(publishedDate.split(" ")[1].toUpperCase()).getMonth()+1;

				Calendar calendar = setTime(publishedDate, articleDay, articleMonth);

				techArticleList.add(new TechArticle(header, imageLink, articleLink, calendar.getTime()));
			}
		}

	}

	@GetMapping("/")
	public ModelAndView showHome() {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("newsList", techArticleList.subList(0, 20));
		modelAndView.addObject("endIndex",2);
		System.out.println("returning");
		return modelAndView;

	}



	@GetMapping("page/{page}")
	public ModelAndView getMoreNews(@PathVariable("page") Integer index) {
		if(index == 0 || techArticleList.size() < (index*20)-20)
			return new ModelAndView("home");

		System.out.println("Page "+index);
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("newsList", techArticleList.subList((index*20)-20 , index*20>techArticleList.size()? techArticleList.size(): index*20 ));
		if(index*20 < techArticleList.size())
			modelAndView.addObject("endIndex", index+1);
		return modelAndView;
	}

	private void limitArticleListSize() {
		if(techArticleList.size() > Constants.MAX_ARTICLE) {
			techArticleList.subList(100, techArticleList.size()).clear();
		}

		System.out.println(techArticleList.size());
	}



}
