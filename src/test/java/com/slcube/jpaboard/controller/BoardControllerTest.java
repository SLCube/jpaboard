package com.slcube.jpaboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slcube.jpaboard.dto.board.BoardSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveTest() throws Exception {
        BoardSaveRequestDto requestDto = BoardSaveRequestDto.builder()
                .title("test board title")
                .content("test board content")
                .author("test board author")
                .build();

        String jsonString = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk());
    }
}
