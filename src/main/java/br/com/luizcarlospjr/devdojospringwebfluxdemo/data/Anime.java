package br.com.luizcarlospjr.devdojospringwebfluxdemo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table("anime")
public class Anime {

    @Id
    private Integer id;

    @NotBlank
    private String name;

}
