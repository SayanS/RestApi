package models;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Message {
    private Long id;
    private Long created;
    private Long updated;
    private Long fileId;
    private String fileName;
    private String type;
    private String fileSize;
    private String message;
    private Long rating;
    private Long version;
    private Long sender;
    private Long updatedBy;
    private String channelType;
    private Integer roomId;
    private List<String> permissions = new ArrayList<>();
    private Boolean starred;
    private Boolean pinned;
    private List<String> directRoomOpponent= new ArrayList<>();//Object
    private List<Map<String,String>> reactions = new ArrayList<>();//Object
    private List<String> messageLinks = new ArrayList<>();//Object
    private Message sharedMessage;
    private String messageType;
    private Boolean system;
    private Boolean userMentioned;

    public Message(){
    }

    public Message(Message message){
        this.id=message.getId();
        this.created=message.getCreated();
        this.updated=message.getUpdated();
        this.fileId=message.getFileId();
        this.fileName=message.getFileName();
        this.type=message.getType();
        this.fileSize=message.getFileSize();
        this.message=message.getMessage();
        this.rating=message.getRating();
        this.version=message.getVersion();
        this.sender=message.getSender();
        this.updatedBy=message.getUpdatedBy();
        this.channelType=message.getChannelType();
        this.roomId=message.getRoomId();
        this.permissions=message.getPermissions();
        this.starred=message.getStarred();
        this.pinned=message.getPinned();
        this.directRoomOpponent=message.getDirectRoomOpponent();
        this.reactions=message.getReactions();
        this.messageLinks=message.getMessageLinks();
        this.sharedMessage=message.getSharedMessage();
        this.messageType=message.getMessageType();
        this.system=message.getSystem();
        this.userMentioned=message.getUserMentioned();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Boolean getStarred() {
        return starred;
    }

    public void setStarred(Boolean starred) {
        this.starred = starred;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public List<String> getDirectRoomOpponent() {
        return directRoomOpponent;
    }

    public void setDirectRoomOpponent(List<String> directRoomOpponent) {
        this.directRoomOpponent = directRoomOpponent;
    }

    public List<Map<String,String>> getReactions() {
        return reactions;
    }

    public void setReactions(List<Map<String,String>> reactions) {
        this.reactions = reactions;
    }

    public List<String> getMessageLinks() {
        return messageLinks;
    }

    public void setMessageLinks(List<String> messageLinks) {
        this.messageLinks = messageLinks;
    }

    public Message getSharedMessage() {
        return sharedMessage;
    }

    public void setSharedMessage(Message sharedMessage) {
        this.sharedMessage = sharedMessage;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public Boolean getUserMentioned() {
        return userMentioned;
    }

    public void setUserMentioned(Boolean userMentioned) {
        this.userMentioned = userMentioned;
    }
}
