package ru.netology.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files", schema = "netology")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @Column(name = "file_byte", nullable = false)
    private byte[] fileByte;

    @Column(name = "file_type", nullable = false)
    private String type;

    @Column(name = "file_size",nullable = false)
    private Long size;


    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
  //  private String login;
}
