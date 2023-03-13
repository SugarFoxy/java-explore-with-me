package ru.practicum.events.comments.model;

import lombok.*;
import ru.practicum.events.event.model.Event;
import ru.practicum.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comment_text")
    private String text;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User commentator;
    @ManyToOne()
    @JoinColumn(name = "event_id")
    private Event event;
    @Column(name = "comment_time")
    private LocalDateTime commentTime;
}
