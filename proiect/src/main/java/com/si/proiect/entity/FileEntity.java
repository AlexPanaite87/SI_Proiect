package com.si.proiect.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "key_id", nullable = false)
    private EncryptionKey key;

    @Column(nullable = false)
    private String name;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String state;

    @Column(name = "file_hash")
    private String fileHash;

    public FileEntity() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public EncryptionKey getKey() { return key; }
    public void setKey(EncryptionKey key) { this.key = key; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getFileHash() { return fileHash; }
    public void setFileHash(String fileHash) { this.fileHash = fileHash; }
}
