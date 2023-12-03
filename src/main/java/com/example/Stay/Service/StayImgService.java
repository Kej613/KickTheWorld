package com.example.Stay.Service;

import com.example.Stay.Entity.StayImg;
import com.example.Stay.Repository.StayImgRepository;
import com.example.Trip.Entity.TripImg;
import com.example.Trip.Repository.TripImgRepository;
import com.example.Trip.Service.FileService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class StayImgService {

    //프로젝트 상대경로 설정
    private final static String uploadPath = System.getProperty("user.dir").concat("\\src\\main\\resources\\static\\stay\\");

    private final StayImgRepository stayImgRepository;

    private final FileService fileService;


    //이미지 저장
    public void saveStayImg(StayImg stayImg, MultipartFile stayImgFile) throws Exception{
        String oriImgName = stayImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(uploadPath, oriImgName,
                    stayImgFile.getBytes());
            imgUrl = "/stay/" + imgName;
        }

        //이미지 정보 저장
        stayImg.updateStayImg(oriImgName, imgName, imgUrl);
        stayImgRepository.save(stayImg);
    }



    //이미지 수정
    public void updateStayImg(Long stayImgId, MultipartFile stayImgFile) throws Exception{
        if(!stayImgFile.isEmpty()){
            StayImg savedStayImg = stayImgRepository.findById(stayImgId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedStayImg.getImgName())) {
                fileService.deleteFile(uploadPath+"/"+ savedStayImg.getImgName());
            }

            String oriImgName = stayImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(uploadPath, oriImgName, stayImgFile.getBytes());
            String imgUrl = "/stay/" + imgName;
            savedStayImg.updateStayImg(oriImgName, imgName, imgUrl);
        }
    }

}
