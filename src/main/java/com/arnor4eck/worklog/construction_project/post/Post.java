package com.arnor4eck.worklog.construction_project.post;

import com.arnor4eck.worklog.construction_project.ConstructionProject;
import com.arnor4eck.worklog.construction_project.post.utils.PostStatus;
import com.arnor4eck.worklog.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Сущность поста, который прикрепляется к полигону
 * */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    /** Уникальный идентификатор поста. База данных генерирует автоматически при сохранении объекта.
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Заголовок поста
     * */
    @Column(nullable = false)
    private String title;

    /** Дата и время создания поста
     * */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** Содержимое поста
     * */
    @Column(nullable = false)
    private String content;

    /** Название файлов, которые прикрепляются к посту
     * */
    @ElementCollection
    @CollectionTable(
            name = "post_files",
            joinColumns = @JoinColumn(name = "post_id")
    )
    @Column(name = "file_path")
    @Builder.Default
    private List<String> files = new ArrayList<>();

    public Post(String title, String content){
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    /** Автор поста
     * @see User
     * */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    /** Полигон, к которому прикреплен пост
     * */
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ConstructionProject object;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PostStatus status;
}
