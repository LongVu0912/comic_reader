package com.api.comic_reader.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chapter_image")
public class ChapterImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private ChapterEntity chapter;

    @Column(name = "image_order", nullable = false)
    private Long imageOrder;

    @Column(name = "image_data", columnDefinition="BYTEA")
    private byte[] imageData;
}
