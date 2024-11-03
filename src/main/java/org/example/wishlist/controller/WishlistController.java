package org.example.wishlist.controller;

import org.example.wishlist.model.UserWishlistDTO;

import org.example.wishlist.model.WishTagDTO;
import org.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class WishlistController {
    //
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("users", wishlistService.getAllUsers());
        model.addAttribute("roles", wishlistService.getAllRoles());
        return "loginpage";
    }


    @GetMapping("/addWish")
    public String addWish(Model model) {
        WishTagDTO wishTagDTO = new WishTagDTO();
        model.addAttribute("wishdto", wishTagDTO);
        model.addAttribute("avaliableTags", wishlistService.getAvaliableTags());
        return "addWish";
    }

    @PostMapping("/addWish")
    public String addAttraction(@ModelAttribute WishTagDTO wishtagdto, Model model) throws Exception {
        UserWishlistDTO userWishlistDTO = wishlistService.getUserwishlistById(wishtagdto.getWishlist_id());
        wishlistService.addWish(wishtagdto, userWishlistDTO);
        return "redirect:/show-wishlist";
    }

    @GetMapping("/login")
    public String login(@RequestParam String role, @RequestParam int userId, Model model) {
        System.out.println("Received userId: " + userId);
        System.out.println("Received role: " + role);
        model.addAttribute("role", role);
        model.addAttribute("userId", userId);
        return "redirect:/showallwishes?userId=" + userId + "&role=" + role;
    }


    @GetMapping("/showallwishes")
    public String showAllDTOWishes(@RequestParam int userId, Model model) {
        System.out.println("show all wishes has userid: " + userId);
        UserWishlistDTO userWishlistDTO = wishlistService.getUserwishlistByUserId(userId);
        System.out.println(userWishlistDTO); //den er null???
        model.addAttribute("userWishlistDTO", userWishlistDTO);
        model.addAttribute("user", wishlistService.getUserNameById(userId));
        model.addAttribute("avaliableTags", wishlistService.getAvaliableTags());
        return "show-wishlist";
    }

    @PostMapping("attractions/delete")
    public String getWishToDelete(@RequestParam String name) {
        //TODO kald den rigtige gettermetoder, når den er blevet lavet.
        //Wish w = wishlistService.getWishByName(name);
        //if (w != null) {

        //    wishlistService.deleteDTOWishlistItem(w);

        //}
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteWishlistItem(@RequestParam int wish_Id) {
        wishlistService.deleteDTOaWishlistItem(wish_Id);
        //TODO indsæt korrekt redirection herunder.
        return "redirect:/show-wishlist";
    }

}
