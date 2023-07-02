package com.jungsuk_2_1.postory.controller;


import com.jungsuk_2_1.postory.dto.*;
import com.jungsuk_2_1.postory.dto.SeriesDto;
import com.jungsuk_2_1.postory.dto.StudioSeriesDto;
import com.jungsuk_2_1.postory.service.ChannelService;
import com.jungsuk_2_1.postory.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("studio/{chnlUri}/series")
public class SeriesController {

    private SeriesService seriesService;

    @Autowired
    SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @PostMapping("/create")
    ResponseEntity<?> createSeries(@AuthenticationPrincipal String userId, @RequestBody SeriesDto seriesDto) {
        try {
            StudioSeriesDto studioSeriesDto = seriesService.createSeries(userId, seriesDto);

            Map<String, Object> data = new HashMap<>();

            if (studioSeriesDto == null) {
                return ResponseEntity.notFound().build(); // StudioSeriesDto가 null인 경우 404 응답
            }

            data.put("series", studioSeriesDto);

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/edit/{seriesId}")
    ResponseEntity<?> updateSeries(@AuthenticationPrincipal String userId, @PathVariable Integer seriesId, @RequestBody SeriesDto seriesDto) {
        try {
            StudioSeriesDto studioSeriesDto = seriesService.updateSeries(userId, seriesId, seriesDto);

            Map<String, Object> data = new HashMap<>();

            if (studioSeriesDto == null) {
                return ResponseEntity.notFound().build(); // StudioSeriesDto가 null인 경우 404 응답
            }

            data.put("series", studioSeriesDto);

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }


    @DeleteMapping("/delete/{serId}")
    public ResponseEntity<?> deleteSeries(@PathVariable Integer serId) {

        try {
            boolean doesExist = seriesService.deleteSeries(serId);

            Map<String, Object> data = new HashMap<>();

            if (doesExist == false) {
                data.put("doesDeleted?", "Success to delete");
            } else {
                data.put("doesDeleted?", "Failed to delete");
            }

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("errMsg", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}