package com.lovePower.butWho.controller.result;

import com.lovePower.butWho.domain.result.Result;
import com.lovePower.butWho.domain.user.User;
import com.lovePower.butWho.dto.result.request.ResultSaveRequest;
import com.lovePower.butWho.dto.result.response.FinalResponse;
import com.lovePower.butWho.dto.result.response.PlayResponse;
import com.lovePower.butWho.dto.result.response.ResultSaveResponse;
import com.lovePower.butWho.dto.result.response.UserInfoResponse;
import com.lovePower.butWho.service.result.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService){
        this.resultService = resultService;
    }

    //캐릭터별 결과 저장
    @PostMapping("/api/result/{character}")
    public ResultSaveResponse saveResult(Authentication authentication,
                                         @RequestBody ResultSaveRequest request, @PathVariable Integer character)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = ((User)userDetails).getEmail();
        return resultService.saveCharacterResult(email,request,character);
    }

    //해당 유저의 모든 공략 후 최종 결과 반환
    @GetMapping("/api/result/final")
    public List<FinalResponse> allResults(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = ((User)userDetails).getEmail();
        return resultService.getFinalResult(email);
    }

    //해당 유저의 공략여부
    @GetMapping("/api/play")
    public List<PlayResponse> isPlayed(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = ((User)userDetails).getEmail();
        return resultService.getPlayed(email);
    }

    //새로 플레이하고 싶은 경우 공략 결과 초기화하기
    @PostMapping("/api/clear")
    public void clearResult(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = ((User)userDetails).getEmail();
        resultService.clearResult(email);
    }

    //유저 정보 결과
    @GetMapping("/api/myInfo")
    public UserInfoResponse getUserInfo(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = ((User)userDetails).getEmail();
        return resultService.getUserInfo(email);
    }

    //테스트용
    @GetMapping("/api/result")
    public ResponseEntity<?> helloUser() {
        return  ResponseEntity.ok().build();
    }
}
