package com.liveguard.payload;

import com.liveguard.dto.ChipDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChipDetailsRequest {

    private String name;
    private List<ChipDetailDTO> details;

}
