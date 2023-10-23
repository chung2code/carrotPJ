//package com.example.demo.controller.user;
//
//
//import com.example.demo.domain.user.dto.Criteria;
//import com.example.demo.domain.user.dto.PageDto;
//import com.example.demo.domain.user.dto.ProductDto;
//import com.example.demo.domain.user.entity.Product;
//import com.example.demo.domain.user.service.ProductService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
//import java.io.IOException;
//import java.security.Principal;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Controller
//@Slf4j
//@RequestMapping("/user/product")
//public class ProductController {
//
//    @Autowired
//    private ProductService productService;
//
//    public static String READ_PRODUCT_DIR_PATH;
//
//
//
//    @GetMapping("/list")
//    public String list( Integer pageNo,String type, String keyword, Model model, HttpServletResponse response ){
//        log.info("GET /user/product/list..."+pageNo);
//
////        //PageDto Start
//
//        Criteria criteria =null;
//        if (pageNo==null){
//            pageNo=1;
//            criteria = new Criteria();
//        }else {
//            criteria = new Criteria(pageNo,10);
//        }
//
//        //--------------------
//        //Search
//        //--------------------
//        criteria.setType(type);
//        criteria.setKeyword(keyword);
//
//       // 서비스 실행
//        Map<String,Object> map = productService.GetProductList(criteria);
//
//        PageDto pageDto = (PageDto) map.get("pageDto");
//        List<Product> list = (List<Product>) map.get("list");
//
//        //Entity -> Dto
//        List<ProductDto> productList = list.stream().map(product ->ProductDto.Of(product)).collect(Collectors.toList());
//
//        //view전달
//        model.addAttribute("productList", productList);
//        model.addAttribute("pageDto", pageDto);
//        model.addAttribute("pageNo", pageNo);
//
//        log.info("Product list: " + productList);
//        log.info("Page DTO: " + pageDto);
//        log.info("Page number: " + pageNo);
//
//
//        return "/user/product/list";
//    }
////add
//    @GetMapping("/add")
//    public void getAddProductPage() {
//        log.info("GET /user/product/add");
//    }
//
//    @PostMapping("/add")
//    public String addProduct( ProductDto productDto, BindingResult result,Model model,
//                             Principal principal, RedirectAttributes redirectAttributes) throws IOException {
//        log.info("POST /user/product/add...." + productDto);
//        // 로그인 확인
//        if (principal == null) {
//            return "redirect:/user/login";
//        }
//
//        // 유효성 체크
//        if (result.hasFieldErrors()) {
//            for (FieldError error :result.getFieldErrors()){
//                log.info(error.getField()+":"+error.getDefaultMessage());
//                model.addAttribute(error.getField(),error.getDefaultMessage());
//            }
//            return "user/product/add";  // 에러가 있으면 다시 제품 추가 페이지로 이동
//        }
//
//        //서비스 실행
//        boolean isSaved = productService.add(productDto);
//
//        if (isSaved){
//            return "redirect:/user/product/list";
//        }
//
//
//        return "redirect:/user/product/add";
//    }
//
//    @GetMapping("/read")
//    public String read(Long Pno, Integer pageNo, Model model, HttpServletRequest request, HttpServletResponse response) {
//        log.info("GET /user/product/read : " + Pno);
//
//        //서비스 실행
//        Product product =  productService.getProductOne(Pno);
//
//        ProductDto dto = new ProductDto();
//        dto.setPno(product.getPno());
//        dto.setTitle(product.getTitle());
//        dto.setDetails(product.getDetails());
//        dto.setReg_date(product.getReg_date());
//        dto.setPlace(product.getPlace());
//        dto.setCount(product.getCount());
//
//
//        System.out.println("FILENAMES : " + product.getFilename());
//        System.out.println("FILESIZES : " + product.getFilesize());
//
//        String filenames[] = null;
//        String filesizes[] = null;
//        if(product.getFilename()!=null){
//            //첫문자열에 [ 제거
//            filenames = product.getFilename().split(",");
//            filenames[0] = filenames[0].substring(1, filenames[0].length());
//            //마지막 문자열에 ] 제거
//            int lastIdx = filenames.length-1;
//            System.out.println("filenames[lastIdx] : " + filenames[lastIdx].substring(0,filenames[lastIdx].lastIndexOf("]")));
//            filenames[lastIdx] = filenames[lastIdx].substring(0,filenames[lastIdx].lastIndexOf("]"));
//
//            model.addAttribute("filenames", filenames);
//        }
//        if(product.getFilesize()!=null){
//            //첫문자열에 [ 제거
//            filesizes = product.getFilesize().split(",");
//            filesizes[0] = filesizes[0].substring(1, filesizes[0].length());
//            //마지막 문자열에 ] 제거
//            int lastIdx = filesizes.length-1;
//            System.out.println("filesizes[lastIdx] : " + filesizes[lastIdx].substring(0,filesizes[lastIdx].lastIndexOf("]")));
//            filesizes[lastIdx] = filesizes[lastIdx].substring(0,filesizes[lastIdx].lastIndexOf("]"));
//
//
//
//            model.addAttribute("filesizes", filesizes);
//        }
//
//
//        if(product.getDirPath()!=null){
//
//            this.READ_PRODUCT_DIR_PATH = product.getDirPath();
//        }
//        model.addAttribute("productDto",dto);
//        model.addAttribute("pageNo",pageNo);
//
//        log.info("productDto",dto);
//        log.info("pageNo",pageNo);
//
//
//        //-------------------
//        // COUNTUP
//        //-------------------
//
//        //쿠키 확인 후  CountUp(/board/read.do 새로고침시 조회수 반복증가를 막기위한용도)
//        Cookie[] cookies = request.getCookies();
//        if(cookies!=null)
//        {
//            for(Cookie cookie:cookies)
//            {
//                if(cookie.getName().equals("reading"))
//                {
//                    if(cookie.getValue().equals("true"))
//                    {
//                        //CountUp
//                        System.out.println("COOKIE READING TRUE | COUNT UP");
//                        productService.count(product.getPno());
//                        //쿠키 value 변경
//                        cookie.setValue("false");
//                        response.addCookie(cookie);
//                    }
//                }
//            }
//        }
//
//        return "user/product/read";
//
//    }
//    @GetMapping("/update")
//    public void update(Long Pno,Model model){
//        log.info("GET /user/product/update Pno" + Pno);
//
//        //서비스 실행
//        Product product = productService.getProductOne(Pno);
//
//        ProductDto dto = new ProductDto();
//        dto.setPno(product.getPno());
//        dto.setTitle(product.getTitle());
//        dto.setDetails(product.getDetails());
//        dto.setPlace(product.getPlace());
//
//        dto.setCount(product.getCount());
//
//
//        System.out.println("FILENAMES : " + product.getFilename());
//        System.out.println("FILESIZES : " + product.getFilesize());
//
//        String filenames[] = null;
//        String filesizes[] = null;
//        if (product.getFilename()!=null) {
//            //첫문자열에 [제거
//            filenames = product.getFilename().split(",");
//            filenames[0] = filenames[0].substring(1,filenames[0].length());
//            //마지막 문자열 ] 제거
//            int lastIdx = filenames.length-1;
//            System.out.println("filelnames[lastIdx]:"+filenames[lastIdx].substring(0,filenames[lastIdx].lastIndexOf("]")));
//            filenames[lastIdx] = filenames[lastIdx].substring(0,filenames[lastIdx].lastIndexOf("]"));
//
//            model.addAttribute("filenames",filenames);
//        }
//        if (product.getFilesize()!=null){
//            filesizes = product.getFilesize().split(",");
//            filesizes[0] =  filesizes[0].substring(1,filesizes[0].length());
//            //마지막 문자 ] 제거
//            int lastIdx = filesizes.length-1;
//            System.out.println("filesize[lastIdx]:" + filesizes[lastIdx].substring(0,filesizes[lastIdx].lastIndexOf("]")));
//            filesizes[lastIdx] = filesizes[lastIdx].substring(0,filesizes[lastIdx].lastIndexOf("]"));
//
//            model.addAttribute("filesizes",filesizes);
//        }
//        if(product.getDirPath()!=null) {
//            model.addAttribute("dirpath", product.getDirPath());
//
//        }
//        model.addAttribute("ProductDto",dto);
//
//
//    }
//
//    @PostMapping("/update")
//    public String Post_update(@Valid ProductDto dto,BindingResult bindingResult,Model model) throws IOException {
//        log.info("POST /user/product/update dto" + dto);
//
//        if (bindingResult.hasFieldErrors()) {
//            for (FieldError error : bindingResult.getFieldErrors()) {
//                log.info(error.getField() + " : " + error.getDefaultMessage());
//                model.addAttribute(error.getField(), error.getDefaultMessage());
//            }
//
//            return "/user/product/read";
//        }
//
//        //서비스 실행
//
//        boolean isadd = productService.updateProduct(dto);
//
//        if (isadd) {
//            return "redirect: /user/product/read?Pno" + dto.getPno();
//        }
//        return "redirect: /user/product/update?Pno=" + dto.getPno();
//
//    }
//    //-----------------댓글-----------------------------
//        //reply delete
//        @GetMapping("/reply/delete/{Pno}/{rno}")
//        public String delete(@PathVariable Long Pno, @PathVariable Long rno)  {
//            log.info("GET /user/product/reply/delete Pno,rno" + rno + " " + rno);
//
//            productService.deleteReply(rno);
//            return "redirect: /user/product/read?Pno" +Pno;
//        }
//
//        @GetMapping("/reply/thumbsup")
//        public String thumbsup(Long Pno,Long rno){
//
//            productService.thumbsUp(rno);
//            return "redirect: /user/product/read?Pno"+Pno;
//
//        }
//
//    }
//
//
//
//
//
//
//
//
//
//
//
