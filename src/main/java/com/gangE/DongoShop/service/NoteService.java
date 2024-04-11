package com.gangE.DongoShop.service;

import com.gangE.DongoShop.model.Note;
import com.gangE.DongoShop.model.NoteProduct;
import com.gangE.DongoShop.model.Product;
import com.gangE.DongoShop.repository.NoteProductRepository;
import com.gangE.DongoShop.repository.NoteRepository;
import com.gangE.DongoShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteProductRepository noteProductRepository;
    private final ProductRepository productRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, NoteProductRepository noteProductRepository, ProductRepository productRepository) {
        this.noteRepository = noteRepository;
        this.noteProductRepository = noteProductRepository;
        this.productRepository = productRepository;
    }

    public Note createNote(String title) {
        Note note = new Note();
        note.setTitle(title);
        return noteRepository.save(note);
    }

    public void connectNoteToProduct(long noteId, long productId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        NoteProduct noteProduct = new NoteProduct();
        noteProduct.setNote(note);
        noteProduct.setProduct(product);
        noteProductRepository.save(noteProduct);
    }

    public void disconnectNoteFromProduct(long noteId, long productId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        NoteProduct noteProduct = noteProductRepository.findByNoteAndProduct(note, product)
                .orElseThrow(() -> new RuntimeException("NoteProduct not found"));
        noteProductRepository.delete(noteProduct);
    }

    public void deleteNote(long noteId) {
        noteRepository.deleteById(noteId);
    }

    public void updateNoteTitle(long noteId, String newTitle) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        note.setTitle(newTitle);
        noteRepository.save(note);
    }
}
