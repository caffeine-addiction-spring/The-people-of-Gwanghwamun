package com.caffeine.gwanghwamun.domain.menu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_menu_options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menu_option_id")
    private UUID menuOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "option_name", nullable = false, length = 255)
    private String optionName;

    @Column(name = "content", length = 255)
    private String content;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "is_sold_out", nullable = false)
    private Boolean isSoldOut = false;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private UUID deletedBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public MenuOption(
            Menu menu,
            String optionName,
            String content,
            Integer price,
            Boolean isSoldOut,
            Boolean isHidden
    ) {
        this.menu = menu;
        this.optionName = optionName;
        this.content = content;
        this.price = price;
        this.isSoldOut = isSoldOut != null ? isSoldOut : false;
        this.isHidden = isHidden != null ? isHidden : false;
    }

}
