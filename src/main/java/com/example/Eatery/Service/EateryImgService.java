package com.example.Eatery.Service;


import com.example.Eatery.Entity.EateryImg;
import com.example.Eatery.Repository.EateryImgRepository;
import jakarta.persistence.EntityNotFoundException;
import com.example.Trip.Service.FileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class EateryImgService {

    //프로젝트 상대경로 설정
    private final static String uploadPath = System.getProperty("user.dir").concat("\\src\\main\\resources\\static\\eatery\\");

    private final EateryImgRepository eateryImgRepository;

    private final FileService fileService;


    //이미지 저장
    public void saveEateryImg(EateryImg eateryImg, MultipartFile eateryImgFile) throws Exception{
        String oriImgName = eateryImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(uploadPath, oriImgName,
                    eateryImgFile.getBytes());
            imgUrl = "/eatery/" + imgName;
        }

        //이미지 정보 저장
        eateryImg.updateEateryImg(oriImgName, imgName, imgUrl);
        eateryImgRepository.save(eateryImg);
    }


    //이미지 수정
    public void updateEateryImg(Long eateryImgId, MultipartFile eateryImgFile) throws Exception{
        if(!eateryImgFile.isEmpty()){
            EateryImg savedEateryImg = eateryImgRepository.findById(eateryImgId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedEateryImg.getImgName())) {
                fileService.deleteFile(uploadPath+"/"+ savedEateryImg.getImgName());
            }

            String oriImgName = eateryImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(uploadPath, oriImgName, eateryImgFile.getBytes());
            String imgUrl = "/eatery/" + imgName;
            savedEateryImg.updateEateryImg(oriImgName, imgName, imgUrl);
        }
    }


}
