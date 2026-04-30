package by.exam.prep.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "weakness_records", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "topic_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeaknessRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(name = "total_attempts", nullable = false)
    @Builder.Default
    private Integer totalAttempts = 0;

    @Column(name = "correct_attempts", nullable = false)
    @Builder.Default
    private Integer correctAttempts = 0;

    @Column(name = "accuracy_rate")
    @Builder.Default
    private Double accuracyRate = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "weakness_level", nullable = false, length = 20)
    @Builder.Default
    private WeaknessLevel weaknessLevel = WeaknessLevel.UNKNOWN;

    @UpdateTimestamp
    @Column(name = "last_practiced_at")
    private LocalDateTime lastPracticedAt;

    public enum WeaknessLevel {
        UNKNOWN, STRONG, MODERATE, WEAK, CRITICAL
    }

    public void recalculate() {
        if (totalAttempts > 0) {
            this.accuracyRate = (double) correctAttempts / totalAttempts * 100;
        }
        if (totalAttempts < 3) {
            this.weaknessLevel = WeaknessLevel.UNKNOWN;
        } else if (accuracyRate >= 80) {
            this.weaknessLevel = WeaknessLevel.STRONG;
        } else if (accuracyRate >= 60) {
            this.weaknessLevel = WeaknessLevel.MODERATE;
        } else if (accuracyRate >= 40) {
            this.weaknessLevel = WeaknessLevel.WEAK;
        } else {
            this.weaknessLevel = WeaknessLevel.CRITICAL;
        }
    }
}
