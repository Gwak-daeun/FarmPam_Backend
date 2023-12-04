package com.fp.backend.controller;

import com.fp.backend.dto.ItemFormDto;
import com.fp.backend.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item/new")
    public ResponseEntity<String> itemNew(@Valid ItemFormDto itemFormDto,
                                          @RequestParam("files") List<MultipartFile> itemImgFileList) {

        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("첫번째 상품 이미지는 필수 입력 값입니다.");
        }
        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 등록 중 에러가 발생했습니다.");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

/*    @GetMapping("/item/list")
    public List<ItemFormDto> getItemList() {
        System.out.println("아이템 요청");
        return itemService.getItemList();
    }*/

/*    @GetMapping("/item/list")
    public List<ItemFormDto> getItemList(@RequestParam Long cursorId,
                                         @RequestParam int size) {
        System.out.println("아이템 요청: " + cursorId);
        return itemService.getItemList(cursorId, PageRequest.of(0, size));
    }*/

        @GetMapping("/item/list")
    public List<ItemFormDto> getItemList(@RequestParam int page,
                                         @RequestParam int size) {
        System.out.println("아이템 요청");
        return itemService.getItemList(page, size);
    }

}
