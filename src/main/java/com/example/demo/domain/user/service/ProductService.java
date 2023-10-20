package com.example.demo.domain.user.service;

import com.example.demo.controller.user.ProductController;
import com.example.demo.domain.user.dto.Criteria;
import com.example.demo.domain.user.dto.PageDto;
import com.example.demo.domain.user.dto.ProductDto;
import com.example.demo.domain.user.dto.ReplyDto;
import com.example.demo.domain.user.entity.Product;
import com.example.demo.domain.user.entity.Reply;
import com.example.demo.domain.user.repository.ProductRepository;
import com.example.demo.domain.user.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
public class ProductService {

    private  String uploadDir = "/upload";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReplyRepository replyRepository;

    //모든 게시물 가져오기
    @Transactional(rollbackFor = SQLException.class)
    public Map<String, Object> GetProductList(Criteria criteria) {

        Map<String, Object> returns = new HashMap<String, Object>();


        //전체게시물 건수 받기
//        int totalcount = (int) productRepository.count();
//        System.out.println("COUNT:" + totalcount);
        int totalcount=0;
        if(criteria!=null&& criteria.getType()!=null) {
            if (criteria.getType().equals("title"))
                totalcount = productRepository.countWhereTitleKeyword(criteria.getKeyword());
            else if (criteria.getType().equals("username"))
                totalcount = productRepository.countWhereDetailsKeyword(criteria.getKeyword());
            else if (criteria.getType().equals("content"))
                totalcount = productRepository.countWherePlaceKeyword(criteria.getKeyword());
        }
        else
            totalcount = (int)productRepository.count();


        System.out.println("COUNT  :" + totalcount);

        //pageDto 만들기
        PageDto pagedto = new PageDto(totalcount, criteria);


        //시작 게시물 번호 구하기 - offset
        int offset = (criteria.getPageno() - 1) * criteria.getAmount();    //1page = 0 ,2page=10

        //search

        List<Product> list = null;
        if(criteria!=null&& criteria.getType()!=null) {
            if (criteria.getType().equals("title")) {
                list =productRepository.findProductTitleAmountStart(criteria.getKeyword(), pagedto.getCriteria().getAmount(), offset);
                System.out.println("TITLE SEARCH!");
                System.out.println(list);
            } else if (criteria.getType().equals("place"))
                list = productRepository.findProductPlaceAmountStart(criteria.getKeyword(), pagedto.getCriteria().getAmount(), offset);
            else if (criteria.getType().equals("details"))
                list = productRepository.findProductDetailsAmountStart(criteria.getKeyword(), pagedto.getCriteria().getAmount(), offset);
            else if (criteria.getType().equals("none"))
                list = productRepository.findProductAmountStart(pagedto.getCriteria().getAmount(), offset);
        }
        else
            list  =  productRepository.findProductAmountStart(pagedto.getCriteria().getAmount(),offset);






        returns.put("list", list);
        returns.put("pageDto", pagedto);

        return returns;

    }


    //post
    @Transactional(rollbackFor = SQLException.class)
    public boolean add(ProductDto dto) throws IOException {

        System.out.println("upload File Count : " + dto.getFiles().length);

        Product product = new Product();
        product.setPno(dto.getPno());
        product.setTitle(dto.getTitle());
        product.setDetails(dto.getDetails());
        product.setPlace(dto.getPlace());
        product.setReg_date(LocalDateTime.now());
        product.setCount(0L);

        MultipartFile[] files = dto.getFiles();

        List<String> filenames = new ArrayList<String>();
        List<String> filesizes = new ArrayList<String>();

        //파일업로드
        if (dto.getFiles().length >= 1 && dto.getFiles()[0].getSize() != 0L) {
            //Upload Dir 미존재시 생성
            String uploadpath = uploadDir + File.separator + dto.getUser() + File.separator + UUID.randomUUID();
            File dir = new File(uploadpath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            //product에 경로 추가
            product.setDirPath(dir.toString());


            for (MultipartFile file : dto.getFiles()) {
                System.out.println("--------------------");
                System.out.println("FILE NAME : " + file.getOriginalFilename());
                System.out.println("FILE SIZE : " + file.getSize() + " Byte");
                System.out.println("--------------------");

                //파일명 추출
                String filename = file.getOriginalFilename();
                //파일객체 생성
                File fileobj = new File(uploadpath, filename);
                //업로드
                file.transferTo(fileobj);

                //filenames 저장
                filenames.add(filename);
                filesizes.add(file.getSize() + "");
            }
        }
        product.setFilename(filenames.toString());
        product.setFilesize(filesizes.toString());

        product = productRepository.save(product);
        boolean issaved = productRepository.existsById(product.getPno());

        return issaved;
    }
    @Transactional(rollbackFor = Exception.class)
    public Product getProductOne(Long Pno) {

        Optional<Product> product = productRepository.findById(Pno);
        if (product.isEmpty()) {
            return null;
        } else
            return product.get();
    }


    @Transactional(rollbackFor = SQLException.class)
    public boolean updateProduct(ProductDto dto) throws IOException{
        System.out.println("upload File Count:" + dto.getFiles().length);
        System.out.println("ProductController.READ_DIRECTORY:" + ProductController.READ_PRODUCT_DIR_PATH);


        Product product = new Product();
        product.setPno(dto.getPno());
        product.setTitle(dto.getTitle());
        product.setDetails(dto.getDetails());
        product.setPlace(dto.getPlace());
        product.setReg_date(LocalDateTime.now());
        product.setCount(0L);

        MultipartFile[] files = dto.getFiles();

        List<String> filenames = new ArrayList<String>();
        List<String> filesizes = new ArrayList<String>();

        //기존 정보 가져오기

        Product oldProduct = productRepository.findById(dto.getPno()).get();

        if (dto.getFiles().length >= 1 && dto.getFiles()[0].getSize()!=0L) {
            //upload dir 미존재시

            //product에 경로 추가

            product.setDirPath(ProductController.READ_PRODUCT_DIR_PATH.toString());

            for (MultipartFile file : dto.getFiles()) {

                System.out.println("--------------------");
                System.out.println("FILE NAME : " + file.getOriginalFilename());
                System.out.println("FILE SIZE : " + file.getSize() + " Byte");
                System.out.println("--------------------");

                //파일명 추출
                String filename = file.getOriginalFilename();
                //파일객체 생성
                File fileobj = new File(ProductController.READ_PRODUCT_DIR_PATH, filename);
                //업로드
                file.transferTo(fileobj);

                //filenames 저장
                filenames.add(filename);
                filesizes.add(file.getSize() + "");
            }

            //기존 파일과 병합

            String oldFilenames = oldProduct.getFilename();
            String oldFilesizes = oldProduct.getFilesize();
            String [] old_fn_arr = oldFilenames.split(",");
            String [] old_fs_arr = oldFilesizes.split(",");

            //첫문자열에 '[' 마지막 ']' 제거
            old_fn_arr[0] = old_fn_arr[0].substring(1, old_fn_arr[0].length());
            int lastIdx = old_fn_arr.length-1;
            old_fn_arr[lastIdx] = old_fn_arr[lastIdx].substring(0,old_fn_arr[lastIdx].lastIndexOf("]"));

            old_fs_arr[0] = old_fs_arr[0].substring(1, old_fs_arr[0].length());
            int lastIdx2 = old_fs_arr.length-1;
            old_fs_arr[lastIdx2] = old_fs_arr[lastIdx2].substring(0,old_fs_arr[lastIdx2].lastIndexOf("]"));


            String newFilenames [] = Stream.concat(Arrays.stream(old_fn_arr),Arrays.stream(filenames.toArray())).toArray(String[]::new);
            String newFilesizes [] = Stream.concat(Arrays.stream(old_fs_arr),Arrays.stream(filesizes.toArray())).toArray(String[]::new);
            System.out.println("newFilenames : " + (Arrays.toString(newFilenames)));
            System.out.println("newFilesizes : " + (Arrays.toString(newFilesizes)));

            product.setFilename(Arrays.toString(newFilenames));
            product.setFilesize(Arrays.toString(newFilesizes));
        }
        else {
            product.setFilename(oldProduct.getFilename());
            product.setFilesize(oldProduct.getFilesize());
        }
        product.setPno(oldProduct.getPno());

        product =  productRepository.save(product);


        return product!=null;


    }
    @Transactional(rollbackFor = SQLException.class)
    public boolean removeFile(String Pno,String filename){
        Long num = Long.parseLong(Pno);
        filename = filename.trim();

        // file delete

        File file = new File(ProductController.READ_PRODUCT_DIR_PATH,filename);
        if(!file.exists()){
            System.out.println("없음..." + ProductController.READ_PRODUCT_DIR_PATH);
            return false;
        }

        //delete DB

        Product readProduct = productRepository.findById(num).get();
        String fn [] = readProduct.getFilename().split(",");
        String fs [] = readProduct.getFilesize().split(",");

        //첫문자열에 '[' 마지막 ']' 제거
        fn[0] = fn[0].substring(1, fn[0].length());
        int lastIdx = fn.length-1;
        fn[lastIdx] = fn[lastIdx].substring(0,fn[lastIdx].lastIndexOf("]"));

        fs[0] = fs[0].substring(1, fs[0].length());
        int lastIdx2 = fs.length-1;
        fs[lastIdx2] = fs[lastIdx2].substring(0,fs[lastIdx2].lastIndexOf("]"));

        List<String> newFn  = new ArrayList();
        List<String> newFs  = new ArrayList();

        for(int i=0;i<fn.length;i++)
        {
            if(!StringUtils.contains(fn[i],filename)){
                System.out.println("보존 FILENAME : " + fn[i]);
                newFn.add(fn[i]);
                newFs.add(fs[i]);
            }


        }

        readProduct.setFilename(newFn.toString());
        readProduct.setFilesize(newFn.toString());
        readProduct = productRepository.save(readProduct);

        return file.delete();

    }

    @Transactional(rollbackFor = SQLException.class)
    public boolean removeProduct(Long Pno){

        Product product = productRepository.findById(Pno).get();
        String removePath = ProductController.READ_PRODUCT_DIR_PATH;


        //파일 있으면삭제

        File dir = new File(removePath);
        if (dir.exists()){
            File files[] = dir.listFiles();
            for (File file : files){
                file.delete();
            }
            dir.delete();
        }
        // delete DB
        productRepository.delete(product);

        return productRepository.existsById(Pno);
    }
    //COUNT
    @Transactional(rollbackFor = SQLException.class)

    public void count(Long Pno){
        Product product = productRepository.findById(Pno).get();
        product.setCount(product.getCount()+1);
        productRepository.save(product);
    }
    //---------------------
    //REPLY
    //---------------------

    //ADD

    public void addReply(Long Pno,String contents,String username){
        Reply reply = new Reply();
        Product product =new Product();
        product.setPno(Pno);

        reply.setProduct(product);
        reply.setContent(contents);
        reply.setUsername(username);
        reply.setReg_date(LocalDateTime.now());
        reply.setLikeCount(0L);

        replyRepository.save(reply);
    }

    //LIST
    public List<ReplyDto>getReplyList(Long Pno) {
        List<Reply> replyList = replyRepository.GetReplyByBnoDesc(Pno);

        List<ReplyDto> returnReply = new ArrayList<>();
        ReplyDto dto = null;

        if (!replyList.isEmpty()) {
            for (int i = 0; i < replyList.size(); i++) {
                dto = new ReplyDto();
                dto.setPno(replyList.get(i).getProduct().getPno());
                dto.setRno(replyList.get(i).getRno());
                dto.setUsername(replyList.get(i).getContent());
                dto.setReg_Date(replyList.get(i).getReg_date());
                dto.setLikeCount(replyList.get(i).getLikeCount());

                returnReply.add(dto);
            }
            return  returnReply;
        }
            return null;
    }

    //reply count

    public Long  getReplyCount(Long Pno){
      return replyRepository.GetReplyCountByBnoDesc(Pno);
    }

    public void deleteReply(Long rno){
        replyRepository.deleteById(rno);
    }
    public void thumbsUp(Long rno){
        Reply reply = replyRepository.findById(rno).get();
        reply.setLikeCount(reply.getLikeCount()+1L);
        replyRepository.save(reply);
    }

}