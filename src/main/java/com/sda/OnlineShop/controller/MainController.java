package com.sda.OnlineShop.controller;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.sda.OnlineShop.dto.ProductDto;
import com.sda.OnlineShop.dto.RegistrationDto;
import com.sda.OnlineShop.dto.SelectedProductDto;
import com.sda.OnlineShop.services.ProductService;
import com.sda.OnlineShop.services.RegistrationService;
import com.sda.OnlineShop.validators.RegistrationDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private ProductService productService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RegistrationDtoValidator registrationDtoValidator;

    //handler care se ocupă de request-uri de tip Get pe /addProduct:
    @GetMapping("/addProduct")
    public String addProductGet(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        //teoretic aici executam business logic
        //dupa care introducem un nume de pagină care este "addProduct"
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProductPost(@ModelAttribute ProductDto productDto,
                                 @RequestParam("productImage") MultipartFile productImage) {

        productService.addProduct(productDto, productImage);
        System.out.println("S-a apelat functionalitatea de addProductPost");
        System.out.println(productDto);
        return "addProduct";
    }

    @GetMapping("/home")
    public String homeGet(Model model) {
        List<ProductDto> productDtos = productService.getAllProductDtos();
        model.addAttribute("productDtos", productDtos);
        return "home";
    }


    @GetMapping("/product/{name}/{productId}")
    public String viewProductGet(@PathVariable(value = "productId") String productId, Model model) {

        Optional<ProductDto> optionalProductDto = productService.getOptionalProductDtoById(productId);
        if (optionalProductDto.isEmpty()) {
            return "error";
        }
        model.addAttribute("productDto", optionalProductDto.get());

        SelectedProductDto selectedProductDto = new SelectedProductDto();
        model.addAttribute("selectedProductDto", selectedProductDto);

        System.out.println("Am dat click pe produsul cu id-ul " + productId);
        return "viewProduct";
    }

    @GetMapping("/registration")
    public String viewRegistrationGet(Model model) {
        RegistrationDto registrationDto = new RegistrationDto();
        model.addAttribute("registrationDto", registrationDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String viewRegistrationPost(@ModelAttribute RegistrationDto registrationDto, BindingResult bindingResult) {
        registrationDtoValidator.validate(registrationDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        registrationService.addRegistration(registrationDto);
        return "registration";
    }

    @GetMapping("/login")
    public String viewLoginGet() {
        return "login";
    }
}