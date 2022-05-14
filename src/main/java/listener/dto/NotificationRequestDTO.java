package listener.dto;

public class NotificationRequestDTO {
    private final Long participationIdx;
    private final String content;

    public NotificationRequestDTO(Long participationIdx, String content) {
        this.participationIdx = participationIdx;
        this.content = content;
    }

    public Long getParticipationIdx() {
        return participationIdx;
    }

    public String getContent() {
        return content;
    }
}
