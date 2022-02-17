package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.BookDto;
import jpabook.jpashop.dto.BookForm;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Item book = Book.createBook(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        List<BookDto> bookDtos = new ArrayList<>();
        for (Item item : items) {
            bookDtos.add(new BookDto(item));
        }
        model.addAttribute("items", bookDtos);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateForm(Model model,
        @PathVariable(value = "itemId") Long itemId){
        Book item = (Book) itemService.findItem(itemId);
        BookForm bookForm = new BookForm(item.getName(), item.getPrice(), item.getStockQuantity(), item.getAuthor(), item.getIsbn());

        model.addAttribute("form", bookForm);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String update(@PathVariable(value = "itemId") Long itemId,
                         @ModelAttribute(value = "form") BookForm form){
        Book book = Book.createBook(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
        itemService.updateBook(itemId, book);
        return "redirect:/";
    }
}
