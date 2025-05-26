package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.domain.DifficultyStatus;
import com.ssafy.questory.dto.request.quest.QuestPositionRequestDto;
import com.ssafy.questory.dto.request.quest.QuestRequestDto;
import com.ssafy.questory.dto.response.quest.QuestRecommendationResponseDto;
import com.ssafy.questory.dto.response.quest.QuestResponseDto;
import com.ssafy.questory.dto.response.quest.QuestsResponseDto;
import com.ssafy.questory.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository questRepository;
    private final MemberAuthService memberAuthService;

    public List<QuestsResponseDto> findQuests(int page, int size) {
        int offset  = (page-1) * size;
        List<QuestsResponseDto> questsResponseDtoList = questRepository.findQuests(offset, size);

        if(questsResponseDtoList.isEmpty()){
            throw new CustomException(ErrorCode.QUEST_LIST_EMPTY);
        }
        return questsResponseDtoList;
    }

    public int getTotalQuests() {
        return questRepository.getTotalQuests();
    }

    public List<QuestsResponseDto> findValidQuestsByMemberEmail(String memberEmail, String difficulty, int page, int size) {
        int offset  = (page-1) * size;
        List<QuestsResponseDto> questsResponseDtoList;
        if(difficulty== null || difficulty.equals("all")){
            questsResponseDtoList = questRepository.findValidQuestsByMemberEmail(memberEmail, offset, size);
        }else{
            questsResponseDtoList = questRepository.findValidQuestsByMemberEmailAndDifficulty(memberEmail, difficulty, offset, size);
        }

        if(questsResponseDtoList.isEmpty()){
            throw new CustomException(ErrorCode.QUEST_LIST_EMPTY);
        }
        return questsResponseDtoList;
    }

    public int getValidQuestsCntByMemberEmail(String memberEmail, String difficulty) {
        if(difficulty== null || difficulty.equals("all")){
            return questRepository.getValidQuestsCntByMemberEmail(memberEmail);
        }else{
            return questRepository.getValidQuestsCntByMemberEmailAndDifficulty(memberEmail, difficulty);
        }
    }


    public List<QuestsResponseDto> findQuestsCreatedByMe(String memberEmail, String difficulty, int page, int size) {
        // memberEmail 검증하는 코드 추가하기

        int offset  = (page-1) * size;
        List<QuestsResponseDto> questsResponseDtoList;
        if(difficulty== null || difficulty.equals("all")){
            questsResponseDtoList = questRepository.findQuestsCreatedByMe(memberEmail, offset, size);
        }else{
            questsResponseDtoList = questRepository.findQuestsCreatedByMeAndDifficulty(memberEmail, difficulty, offset, size);
        }

        if(questsResponseDtoList.isEmpty()){
            throw new CustomException(ErrorCode.QUEST_LIST_EMPTY);
        }
        return questsResponseDtoList;
    }

    public int getQuestsCntCreatedByMe(String memberEmail, String difficulty) {
        if(difficulty== null || difficulty.equals("all")){
            return questRepository.getTotalQuestsCreatedByMe(memberEmail);
        }else{
            return questRepository.getTotalQuestsCreatedByMeAndDifficulty(memberEmail, difficulty);
        }
    }

    public void saveQuest(QuestRequestDto questRequestDto, String memberEmail) {
        int isValidAttraction = questRepository.getAttractionById(questRequestDto.getAttractionId());
        if(isValidAttraction!=1){
            throw new CustomException(ErrorCode.ATTRACTION_NOT_FOUND);
        }

        int contentTypeId = questRepository.getContentTypeIdByAttractionId(questRequestDto.getAttractionId());
        questRepository.saveQuest(questRequestDto, memberEmail, contentTypeId);
    }

    public void modifyQuest(int questId, QuestRequestDto questRequestDto, String memberEmail) {
        int isValidAttraction = questRepository.getAttractionById(questRequestDto.getAttractionId());
        if(isValidAttraction!=1){
            throw new CustomException(ErrorCode.ATTRACTION_NOT_FOUND);
        }

        int attractionId = questRepository.getAttractionIdByQuestId(questId);
        if(attractionId != questRequestDto.getAttractionId()){
            throw new CustomException(ErrorCode.ATTRACTION_NOT_MATCH);
        }

        String memberEmailByQuestId = questRepository.getmemberEmailByQuestId(questId);
        if(!memberEmailByQuestId.equals(memberEmail)){
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }

        questRepository.modifyQuest(questId, questRequestDto);
    }

    public void deleteQuest(int questId, String memberEmail) {
        int questCntByQuestId = questRepository.getQuestCntByQuestId(questId);
        if(questCntByQuestId!=1){
            throw new CustomException(ErrorCode.QUEST_NOT_FOUND);
        }

        String memberEmailByQuestId = questRepository.getmemberEmailByQuestId(questId);
        if(!memberEmailByQuestId.equals(memberEmail)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }

        int validQuestCntByQuestId = questRepository.getValidQuestCntByQuestId(questId);
        if(validQuestCntByQuestId!=1){
            throw new CustomException(ErrorCode.QUEST_ALREADY_DELETED);
        }

        questRepository.deleteQuest(questId);
    }

    public QuestResponseDto findQuestById(int questId) {
        return questRepository.findQuestById(questId);
    }

    public List<QuestsResponseDto> findActiveQuestsByMemberEmail(String memberEmail, String difficulty, int page, int size) {
        int offset  = (page-1) * size;
        List<QuestsResponseDto> questsResponseDtoList;
        if(difficulty== null || difficulty.equals("all")){
            questsResponseDtoList = questRepository.findActiveQuestsByMemberEmail(memberEmail, offset, size);
        }else{
            questsResponseDtoList = questRepository.findActiveQuestsByMemberEmailAndDifficulty(memberEmail, difficulty, offset, size);
        }

        if(questsResponseDtoList.isEmpty()){
            throw new CustomException(ErrorCode.QUEST_LIST_EMPTY);
        }
        return questsResponseDtoList;
    }

    public int getActiveQuestsByMemberEmail(String memberEmail, String difficulty) {
        if(difficulty== null || difficulty.equals("all")){
            return questRepository.getActiveQuestsByMemberEmail(memberEmail);
        }else{
            return questRepository.getActiveQuestsByMemberEmailAndDifficulty(memberEmail, difficulty);
        }
    }

    public void cancelQuest(int questId, String memberEmail) {
        int questCntByQuestId = questRepository.getQuestCntByQuestId(questId);
        if(questCntByQuestId != 1) {
            throw new CustomException(ErrorCode.QUEST_NOT_FOUND);
        }

        int inProgressQuestCntByQuestId = questRepository.getInProgressQuestCntByQuestId(questId);
        if(inProgressQuestCntByQuestId==0){
            throw new CustomException(ErrorCode.QUEST_ALREADY_COMPLETED);
        }

        questRepository.cancelQuest(questId);
    }

    public void startQuest(int questId, String memberEmail) {
        int questCntByQuestId = questRepository.getQuestCntByQuestId(questId);
        if(questCntByQuestId!=1){
            throw new CustomException(ErrorCode.QUEST_NOT_FOUND);
        }

        int memberQuestCntByQuestId = questRepository.getMemberQuestCntByQuestId(questId, memberEmail);
        if(memberQuestCntByQuestId!=0){
            throw new CustomException(ErrorCode.QUEST_ALREADY_STARTED);
        }

        questRepository.startQuest(questId, memberEmail);
    }

    public void completeQuest(int questId, QuestPositionRequestDto questPositionRequestDto, String memberEmail) {
        int questCntByQuestId = questRepository.getQuestCntByQuestId(questId);
        if(questCntByQuestId!=1){
            throw new CustomException(ErrorCode.QUEST_NOT_FOUND);
        }

        double distance = calculateDistance(questPositionRequestDto.getAttractionLatitude(), questPositionRequestDto.getAttractionLongitude(), questPositionRequestDto.getUserLatitude(), questPositionRequestDto.getUserLongitude());
        System.out.println("distance : "+distance);

        if(distance < 1){   // 관광지와 사용자의 거리가 1km 미만이여야 퀘스트 성공
            questRepository.completeQuest(questId, memberEmail);

            // 퀘스트 난이도 조회하기
            String difficulty = questRepository.getDifficultyByQuestId(questId).toUpperCase();
            int exp = memberAuthService.getMemberExp(memberEmail);

            // 난이도에 따라서 경험치 더해주기
            if(difficulty.equals("EASY")){
                memberAuthService.setMemberExp(memberEmail, exp+DifficultyStatus.EASY.getExperiencePoints());
            }else if(difficulty.equals("MEDIUM")){
                memberAuthService.setMemberExp(memberEmail, exp+DifficultyStatus.MEDIUM.getExperiencePoints());
            }else if(difficulty.equals("HARD")) {
                memberAuthService.setMemberExp(memberEmail, exp + DifficultyStatus.HARD.getExperiencePoints());
            }
        }
        else{
            throw new CustomException(ErrorCode.QUEST_LOCATION_TOO_FAR);
        }
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;

        return dist;
    }

    public double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public List<QuestRecommendationResponseDto> getRecommendation(int limit) {
        return questRepository.findRecommendedQuests(limit);
    }
}
