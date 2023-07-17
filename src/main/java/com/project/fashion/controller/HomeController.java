package com.project.fashion.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.fashion.dao.UserDao;

@Controller
public class HomeController {
	UserDao userDao=new UserDao();
	
	@GetMapping("/shorts")
	public String getShorts()
	{
		return "shorts";
	}
	@GetMapping("/short")
	public String showShortsList(Model model)
	{
		String name="Shorts";
		model.addAttribute("shortlist",userDao.allProductList(name));
		return "shorts";
	}
	
	@GetMapping("/nightsuits")
	public String getNightSuits()
	{
		return "nightsuits";
	}
	@GetMapping("/night")
	public String showNightSuitsList(Model model)
	{
		String name="Night Suits";
		model.addAttribute("nightsuitslist",userDao.allProductList(name));
		return "nightsuits";
	}
	
	
	
	
	@GetMapping("/plazzos")
	public String getPlazzos()
	{
		return "plazzos";
	}
	@GetMapping("/plazzo")
	public String showAllPlazzoList(Model model)
	{
		String name="Plazzos";
		model.addAttribute("plazzoList",userDao.allProductList(name));
		return "plazzos";
	}
	 
	@GetMapping("/jumpsuits")
	public String getJumpSuits()
	{
		return "jumpsuits";
	}
	
	@GetMapping("/jump")
	public String showJumpSuits(Model model)
	{
		String name="Jump Suits";
		model.addAttribute("jumpsuitslist",userDao.allProductList(name));
		return "jumpsuits";
	}
	
	@GetMapping("/jeans")
	public String getJeans()
	{
		return "jeans";
	}
	@GetMapping("/jean")
	public String showJeansPage(Model model)
	{
		String name="Jeans";
		model.addAttribute("jeanslist",userDao.allProductList(name));
		return "jeans";
	}
	
	@GetMapping("sweaters")
	public String getSweaters()
	{
		return "sweaters";
	}
	@GetMapping("/sweater")
	public String showSweatersList(Model model)
	{
		String name="Sweaters";
		model.addAttribute("SweatersList",userDao.allProductList(name));
		return "sweaters";
		
	}
	
	
	
	@GetMapping("/jeggins")
	public String getjeggins()
	{
		return "jeggins";
	}
	
	@GetMapping("/jeggin")
	public String showjeggins(Model model)
	{
		String name="Jeggins";
		model.addAttribute("jegginlist",userDao.allProductList(name));
		return "jeggins";
	}
	
	@GetMapping("/tops")
	public String getTops()
	{
		return "tops";
	}
	@GetMapping("/top")
	public String showTops(Model model)
	{
		String name="Tops";
		model.addAttribute("topslist",userDao.allProductList(name));
		return "tops";
	}
	@GetMapping("/duppata")
	public String getDuppatas()
	{
		return "duppata";
	}
	@GetMapping("/duppatas")
	public String showDuppatas(Model model)
	{
		String name="Duppatas";
		model.addAttribute("dupatalist",userDao.allProductList(name));
		return "duppata";
	}
	@GetMapping("/allkurtas")
	public String allKurtaSets()
	{
		return "allkurtas";
	}
	@GetMapping("/sets")
	public String showAllKurtaSets(Model model)
	{
	String name="Kurta Sets";
	model.addAttribute("allkurtasets",userDao.allProductList(name));
	return "allkurtas";	
	}
	@GetMapping("/cottonkurti")
	public String getCottonkurti()
	{
	 return "cottonkurti";	
	}
	@GetMapping("/cottonkurtis")
	public String showCottonKurti(Model model)
	{
		String name="Cotton Kurtis";
		model.addAttribute("cottonkurti",userDao.allProductList(name));
		return "cottonkurti";
	}
	@GetMapping("/embroidery")
	public String getEmbroideryKurti()
	{
		return "embroidery";
	}
	@GetMapping("/ekurti")
	public String showEmbroideryKurti(Model model)
	{
		String name="Embroidery Kurtis";
		model.addAttribute("embroiderykurti",userDao.allProductList(name));
		return "embroidery";
	}
	
	@GetMapping("/lehanga")
	public String getLehangas()
	{
		return "lehanga";
		
	}
	@GetMapping("/lehangas")
	public String showLehangas(Model model)
	{
		String name="Lehanga";
		model.addAttribute("lehangalist",userDao.allProductList(name));
		return "lehanga";
	}
	
	@GetMapping("/gowns")
	public String getGowns()
	{
		return "gowns";
	}
	@GetMapping("/gown")
	public String showGownList(Model model)
	{
		String name="Gown";
		model.addAttribute("gownlist",userDao.allProductList(name));
		return "gowns";
	}
	
	
	@GetMapping("/rayonkurti")
	public String getAllkurti()
	{
		return "rayonkurti";
	}
	@GetMapping("/rkurti")
	public String showAllKurti(Model model)
	{
		String name="Rayon Kurti";
		model.addAttribute("rayonkurti",userDao.allProductList(name));
		return "rayonkurti";
		
	}
	
	@GetMapping("/anarkali")
	public String getAllSarees() {
		// model.addAttribute();
		return "anarkali";
	}
	
	@GetMapping("/anarkalis")
	public String showAllsarees(Model model) 
	{
		String name="Cotton Sarees";
		model.addAttribute("anarkalilist",userDao.allProductList(name));
		return "anarkali";
	}
	@GetMapping("/silksaree")
	public String getSilkSaree()
	{
		return "silksaree";
	}
	@GetMapping("/silk")
	public String showSilkSaree(Model model)
	{
		String name="Silk Sarees";
		model.addAttribute("silksaree",userDao.allProductList(name));
		return "silksaree";
	}
	@GetMapping("/cottonsaree")
	public String getCottonSaree()
	{
		return "cottonsaree";
	}
	
	@GetMapping("/cotton")
	public String showCottonSaree(Model model)
	{
		String name="Cotton Sarees";
		model.addAttribute("cottonsaree",userDao.allProductList(name));
		return "cottonsaree";
	}
	@GetMapping("/georgette")
	public String getGeorgetteSaree(Model model)
	{
		String name="Georgette Sarees";
		model.addAttribute("georgettesaree",userDao.allProductList(name));
		return "georgette";
	}
	
	@GetMapping("/chiffon")
	public String getChiffonSarees()
	{
		return "chiffon";
	}
	@GetMapping("/chiffons")
	public String showChiffonSarees(Model model)
	{
		String name="Chiffon Sarees";
		model.addAttribute("chiffonsaree",userDao.allProductList(name));
		return "chiffon";
		
	}
	

}
