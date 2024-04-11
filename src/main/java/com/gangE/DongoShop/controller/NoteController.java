package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.model.Note;
import com.gangE.DongoShop.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Note Controller", description = "노트")
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Operation(summary = "Create a new note", description = "새로운 노트를 생성합니다.")
    @PostMapping("/create")
    public Note createNote(@RequestParam String title) {
        return noteService.createNote(title);
    }

    @Operation(summary = "Connect note to product", description = "노트를 제품에 연결합니다.")
    @PostMapping("/connect")
    public void connectNoteToProduct(@RequestParam long noteId, @RequestParam long productId) {
        noteService.connectNoteToProduct(noteId, productId);
    }

    @Operation(summary = "Disconnect note from product", description = "노트와 제품의 연결을 해제합니다.")
    @PostMapping("/disconnect")
    public void disconnectNoteFromProduct(@RequestParam long noteId, @RequestParam long productId) {
        noteService.disconnectNoteFromProduct(noteId, productId);
    }

    @Operation(summary = "Delete note", description = "노트를 삭제합니다.")
    @DeleteMapping("/delete")
    public void deleteNote(@RequestParam long noteId) {
        noteService.deleteNote(noteId);
    }

    @Operation(summary = "Update note title", description = "노트 제목을 업데이트합니다.")
    @PutMapping("/update")
    public void updateNoteTitle(@RequestParam long noteId, @RequestParam String newTitle) {
        noteService.updateNoteTitle(noteId, newTitle);
    }
}
