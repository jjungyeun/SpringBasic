package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId)
                .orElse(null);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String save(@ModelAttribute Item item){
        itemRepository.save(item);

//        // model에 직접 넣어주지 않아도됨. ModelAttribute가 알아서 model에 넣어감
//        // ModelAttribute에 이름을 생략하면 클래스명을 첫번째만 소문자로 바꿔서 model에 넣어줌 (Item -> item)
//        model.addAttribute("item", item);

        // 새로고침 시 계속 form이 제출되는 문제를 방지하기 위해, 상품 상세 페이지로 redirect
        return "redirect:/basic/items/" + item.getId();
    }

//    @PostMapping("/add")
    public String save2(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status", true);

        // redirect attribute에 넣은 attribute를 사용할 수 있음
        // 사용하지 않은 정보는 query parameter로 들어감
        return "redirect:/basic/items/{itemId}";
    }

    @PostMapping("/add")
    public String save3(@ModelAttribute ItemDto createDto, RedirectAttributes redirectAttributes){
        Item item = new Item(createDto.getName(), createDto.getPrice(), createDto.getQuantity());
        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);

        // redirect attribute에 넣은 attribute를 사용할 수 있음
        // 사용하지 않은 정보는 query parameter로 들어감
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId)
                .orElse(null);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,
                       @ModelAttribute ItemDto updateDto,
                       Model model){
        Item item = itemRepository.update(itemId, updateDto)
                .orElse(null);
        model.addAttribute("item", item);
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 15000, 20));
    }
}
