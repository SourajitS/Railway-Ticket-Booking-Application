package com.railway.application.controllers.admin;

import com.railway.application.config.AppConstants;
import com.railway.application.dto.PagedResponse;
import com.railway.application.dto.StationDto;
import com.railway.application.service.StationService;
import com.railway.application.service.impl.StationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/stations")
public class StationController {

     private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping
    public ResponseEntity<StationDto> createStation(@Valid @RequestBody StationDto stationDto)
    {

        StationDto dto=stationService.createStation(stationDto);
        return new ResponseEntity<StationDto>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public PagedResponse<StationDto> listStations(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE) int page,
            @RequestParam (defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam (defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam (defaultValue = AppConstants.DEFAULT_SORT_DIR) String sortDir
    )
    {
        PagedResponse<StationDto> stationDtoPagedResponse = stationService.listStations(page, size, sortBy, sortDir);
        return stationDtoPagedResponse;
    }

    @GetMapping("/{id}")
    public StationDto findById(@PathVariable Long id)
    {
        return stationService.findById(id);
    }

    @PutMapping("/{id}")
    public StationDto update(@PathVariable Long id, @RequestBody StationDto stationDto)
    {
        return stationService.update(id, stationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        stationService.delete(id);
      return new ResponseEntity<>(HttpStatus.OK);
    }
}
