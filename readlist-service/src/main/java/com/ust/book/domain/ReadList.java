package com.ust.book.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="readlist")
public class ReadList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long readListId;
    private String username;
    private long isbn;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Kolkata")
    private LocalDateTime lastUpdate;

}
