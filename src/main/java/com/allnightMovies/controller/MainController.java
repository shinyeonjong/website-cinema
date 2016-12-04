package com.allnightMovies.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.allnightMovies.di.Action;
import com.allnightMovies.model.data.MainMenu;
import com.allnightMovies.model.data.cinemaInfo.CinemaNoticeBoardDTO;
import com.allnightMovies.model.data.movieInfo.MainPageEventDTO;
import com.allnightMovies.model.data.movieInfo.MovieBasicInfo;
import com.allnightMovies.model.data.movieInfo.MovieCurrentFilmDTO;
import com.allnightMovies.model.params.Params;
import com.allnightMovies.service.DBService;

@RestController
public class MainController {
	@Autowired
	DBService service;
	
	@RequestMapping(value="/")
	public ModelAndView controller() throws Exception {
		ModelAndView mav = new ModelAndView("template");
		List<MainMenu> list = this.service.getMenus();
		List<MainPageEventDTO> mainEventDTO = this.service.getMainEvnetImg();		//상단 이벤트 이미지
		int mainEventImg = mainEventDTO.size();
		List<MovieCurrentFilmDTO> getNewFilmDTO = this.service.getNewFilmDTO();		//최신상영작 DTO
		List<CinemaNoticeBoardDTO> mainNoticeDTO = this.service.getMainNoticeDTO();	//공지사항 DTO
		mav.addObject("directory", "include");
		mav.addObject("page", "mainPage");
		mav.addObject("contentCSS", "mainPage");
//		mav.addObject("contentjs", "mainPage");
		mav.addObject("mainEventImg", mainEventImg);
		mav.addObject("mainEventDTO", mainEventDTO);
		mav.addObject("NewFilmDTO", getNewFilmDTO);
		mav.addObject("mainNoticeDTO",mainNoticeDTO);
		mav.addObject("list", list);
		
		
//		
//		try {
//			this.service.doTestInsert();
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		return mav;
	}
	
	@RequestMapping(value="/movie/{service}/{method}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView menuCliecked(
			@PathVariable("service") String service, 
			@PathVariable("method") String method, 
			Params params, 
			HttpServletRequest request) throws Throwable {

		HttpSession session = request.getSession();
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		Action action = (Action) context.getBean(service);
		params.setMethod(method);
		params.setRequest(request);
		params.setSession(session);
		ModelAndView mav = action.execute(params);
		
		return mav;
	}
	
	
	@RequestMapping(value="/movie/file", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	
	public ModelAndView fileUpload(
			Params params, 
			MultipartHttpServletRequest multiReq) throws Throwable {

		String posterDir = "C:/workspace/allnightMovies/src/main/webapp/WEB-INF/resources/img/poster/";
		String stilcutDir = "C:/workspace/allnightMovies/src/main/webapp/WEB-INF/resources/img/stillcut/";

		MovieBasicInfo movieBasicInfo = new MovieBasicInfo();
		
		String movieTitle 		= params.getManagerMovieTitle();
		String movieGenre 		= params.getManagerMovieGenre(); 
		String movieDirector 	= params.getManagerMovieDirector();
		String movieAuthor  	= params.getManagerMovieAuthor();
		String movieCast  		= params.getManagerMovieCast();
		String movieReleaseDate = params.getManagerMovieReleaseDate();
		String movieIntro  		= params.getManagerMovieIntro();
		Integer movieAge  		= params.getManagerMovieAge();
		Integer movieRuntime  	= params.getManagerMovieRuntime();
		List<MultipartFile> files = multiReq.getFiles("file");

		try {
			   
			for(int i = 0, size = files.size(); i < size; i++) {
				String dir = i == 0 ? posterDir : stilcutDir;
				
				String fileName = files.get(i).getOriginalFilename();
				byte[] b = files.get(i).getBytes();
				String saveFileName = System.currentTimeMillis() + fileName;
				File saveFile = new File(dir + saveFileName);
				FileOutputStream fos = new FileOutputStream(saveFile);
				fos.write(b);
				fos.close();
				
				//1. 영화 정보  movieBasicInfo
				movieBasicInfo.setMovieTitle(movieTitle);
				movieBasicInfo.setMovieDirector(movieDirector);
				movieBasicInfo.setMovieAuthor(movieAuthor);
				movieBasicInfo.setMovieCast(movieCast);
				movieBasicInfo.setMovieIntro(movieIntro);
				movieBasicInfo.setMovieReleaseDate(movieReleaseDate);
				movieBasicInfo.setMovieGenre(movieGenre);
				movieBasicInfo.setMovieAgeLimit(movieAge);
				movieBasicInfo.setMovieRuntime(movieRuntime);
				
				System.out.println(movieTitle 		);
				System.out.println(movieGenre 		);
				System.out.println(movieDirector 	);
				System.out.println(movieAuthor  	);
				System.out.println(movieCast  		);
				System.out.println(movieReleaseDate );
				System.out.println(movieIntro  		);
				System.out.println(movieAge			);
				System.out.println(movieRuntime		);
				System.out.println(fileName			);
				
				
				if (i != 0) {
//					stillcutImgs.add(fileName);
					this.service.insertStillcut(saveFileName, movieTitle);
				} else {
					movieBasicInfo.setMoviePoster(saveFileName);
					this.service.insertNewMovie(movieBasicInfo);
				}
				Integer movieNO = this.service.getMovieNO(movieTitle);
				String requestUrl = URLEncoder.encode(movieTitle, "UTF-8");
				params.setLocationPath("/movie/mainService/movieDetailInfo?movieInfoTitle="+ requestUrl +"&movieNO=" + movieNO);
			}                         
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("결과 경로 >> " + params.getLocationPath());
		return  new ModelAndView("redirect:" + params.getLocationPath());
	}
	
//	public String redirect() {
//	"redirect:/" + params.getLocationPath();
//		 return "redirect:/url.do";
//		 // return new ModelAndView("redirect:url.do");
	
//	for(MultipartFile file : files) {
//	String fileName = file.getOriginalFilename();
//	byte[] b = file.getBytes();
//	File saveFile = new File(posterDir + System.currentTimeMillis() + fileName);
//	FileOutputStream fos = new FileOutputStream(saveFile);
//	fos.write(b);
//	fos.close();
//}	
	
	
// 이러한 방식의 controller 사용도 가능.
//	@RequestMapping(value="/")
//	public String controller(@RequestParam Map<String, String> map, ModelMap modelMap) {
//		modelMap.put("x", map.get("x"));
//		modelMap.put("y", map.get("y"));
//		return "/sum/result";
//	}
//	
//	@RequestMapping(value="/")
//	public String controller(@ModelAttribute UserPersonalInfoDTO userDTO, Model model) {
//		model.addAttribute("x", userDTO.getUserID());
//		model.addAttribute("y", userDTO.getUserPWD());
//		return "/sum/result";
//	}
}