package com.sdu.edu.service;

import com.sdu.edu.models.Library;
import com.sdu.edu.pojo.LibraryDto;
import com.sdu.edu.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepo;

    public List<LibraryDto> getAllBooks(){
        List<Library> books = libraryRepo.findAll();
        List<LibraryDto> list = new ArrayList<>();
        for(Library book: books){
            LibraryDto library = new LibraryDto();
            library.setId(book.getId());
            library.setAuthor(book.getAuthor());
            library.setTitle(book.getBookName());
            library.setUrl("/file/" + book.getImagePath());
            list.add(library);
        }

        return list;
    }
}
