package com.project.fashion.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.fashion.dao.UserDao;

@Controller
public class HomeController {
	UserDao userDao=new UserDao();
	
	@GetMapping("/filter")
	public String getChiffonSarees()
	{
		return "filter";
	}
	@GetMapping("/wanted")
	public String showChiffonSarees(Model model)
	{
		String name="Chiffon Sarees";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/silksaree")
	public String showSilkSaree(Model model)
	{
		String name="Silk Sarees";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/rayonkurti")
	public String showAllKurti(Model model)
	{
		String name="Rayon Kurti";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/allkurtas")
	public String showAllKurtaSets(Model model)
	{
	String name="Kurta Sets";
	model.addAttribute("filterlist",userDao.allProductList(name));
	return "filter";	
	}
	@GetMapping("/duppata")
	public String showDuppatas(Model model)
	{
		String name="Duppatas";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/cottonsaree")
	public String showCottonSaree(Model model)
	{
		String name="Cotton Sarees";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/embroidery")
	public String showEmbroideryKurti(Model model)
	{
		String name="Embroidery Kurtis";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/lehanga")
	public String showLehangas(Model model)
	{
		String name="Lehanga";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/georgette")
	public String getGeorgetteSaree(Model model)
	{
		String name="Georgette Sarees";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/cottonkurti")
	public String showCottonKurti(Model model)
	{
		String name="Cotton Kurtis";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/gowns")
	public String showGownList(Model model)
	{
		String name="Gown";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/tops")
	public String showTops(Model model)
	{
		String name="Tops";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/jeans")
	public String showJeansPage(Model model)
	{
		String name="Jeans";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	@GetMapping("/nightsuits")
	public String showNightSuitsList(Model model)
	{
		String name="Night Suits";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/jeggins")
	public String showjeggins(Model model)
	{
		String name="Jeggins";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/plazzo")
	public String showAllPlazzoList(Model model)
	{
		String name="Plazzos";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	@GetMapping("/shorts")
	public String showShortsList(Model model)
	{
		String name="Shorts";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/sweaters")
	public String showSweatersList(Model model)
	{
		String name="Sweaters";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	 
	@GetMapping("/jumpsuits")
	public String showJumpSuits(Model model)
	{
		String name="Jump Suits";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/anarkali")
	public String showAllsarees(Model model) 
	{
		String name="Anarkali Kurti";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/shirt")
	public String showAllMenShirt(Model model)
	{
		String name="Shirt";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	@GetMapping("/tshirt")
	public String showAllTshirt(Model model)
	{
		String name="Tshirt";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	
	@GetMapping("/jeansmen")
	public String showMenJeans(Model model)
	{
		String name="Men Jeans";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
	@GetMapping("/mentrack")
	public String showMenTreackList(Model model)
	{
		String name="Men Tracks";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
		
	}
	@GetMapping("/kids")
	public String showAllKids(Model model)
	{
		String name="Kids";
		model.addAttribute("filterlist",userDao.allProductList(name));
		return "filter";
	}
}
