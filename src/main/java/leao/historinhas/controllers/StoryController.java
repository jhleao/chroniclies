package leao.historinhas.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import leao.historinhas.models.IpStoryBlock;
import leao.historinhas.models.Story;
import leao.historinhas.models.StoryStatus;
import leao.historinhas.repositories.IpStoryBlockRepository;
import leao.historinhas.repositories.StoryRepository;
import leao.historinhas.requests.NewStoryRequest;

@Controller
public class StoryController {

  private final StoryRepository storyRepository;
  private final IpStoryBlockRepository ipStoryBlockRepository;
  private JavaMailSender javaMailSender;

  public StoryController(StoryRepository storyRepository, 
  IpStoryBlockRepository ipStoryBlockRepository, JavaMailSender javaMailSender){
    this.storyRepository = storyRepository;
    this.ipStoryBlockRepository = ipStoryBlockRepository;
    this.javaMailSender= javaMailSender;
  }
  
  @RequestMapping("/admin")
  public String generateAdminPage(Model model, HttpServletRequest request){
    System.out.println("Gerando página admin...");

    List<Story> stories = getPendingStories();
    model.addAttribute("stories", stories);

    System.out.println("Quantidade de historinhas pendentes: " + stories.size());

    return "admin";
  }

  @RequestMapping("/sair")
  public String logout(Model model, HttpServletRequest request){
    return generateMainPage(model, request);
  }

  @PostMapping("/admin/aceitar/{id}")
  public ModelAndView acceptStory(@PathVariable("id") Integer id,
      ModelMap model, HttpServletRequest request){

    System.out.println("Aceitando historinha " + id + "...");

    Optional<Story> optionalStory = storyRepository.findById(id);

    if(optionalStory.isPresent()){
      Story story = optionalStory.get();
      story.approve(javaMailSender);
      storyRepository.save(story);
    }

    return new ModelAndView("redirect:/admin", model); 
  }

  @PostMapping("/admin/recusar/{id}")
  public ModelAndView refuseStory(@PathVariable("id") Integer id,
      ModelMap model, HttpServletRequest request){

    System.out.println("Recusando historinha " + id + "...");

    storyRepository.deleteById(id);

    return new ModelAndView("redirect:/admin", model);
  }
  
  @RequestMapping("/enviar")
  public String generateFormPage(Model model, HttpServletRequest request){
    return "enviar";
  }
  
  @PostMapping("/enviar")
  public String receiveNewStory(@ModelAttribute("newStory") NewStoryRequest incomingStory, 
    Model model, HttpServletRequest request){
    System.out.println("Alguma coisa chegou...");
    System.out.println("Recebidinhos: " + incomingStory);

    Story newStory = new Story(incomingStory.getContent(), incomingStory.getEmail());

    storyRepository.save(newStory);

    return "obrigado";
  }
  
  @RequestMapping("/")
  public String generateMainPage(Model model, HttpServletRequest request){

    System.out.println("|-------- Vc entrou no historinhas --------|");
    
    String requestIp = request.getHeader("X-Real-IP");
    System.out.println("Seu ip é... " + requestIp);

    Story story = getRandomStory(requestIp);

    model.addAttribute("story", story);

    return "historinha";
  }

  public List<Story> getPendingStories() {
    return storyRepository.findAllByStatus(StoryStatus.PENDING);
  }

  public Story getRandomStory(String requestIp){
    List<IpStoryBlock> ipStoryBlocks = ipStoryBlockRepository.findAllByIp(requestIp);
    System.out.println("Os IP blocks são: " + ipStoryBlocks);

    List<Story> blockedStories = ipStoryBlocks.stream().map(IpStoryBlock::getStory).collect(Collectors.toList());
    System.out.println("As historinhas bloqueadas são: " + blockedStories);

    List<Story> approvedStories = storyRepository.findAllByStatus(StoryStatus.APPROVED);

    List<Story> allowedStories = approvedStories.stream().filter(s -> !blockedStories.contains(s)).collect(Collectors.toList());

    System.out.println("Ainda tem " + allowedStories.size() + " historinhas pra vc ver");

    if(allowedStories.size() < 1) {
      System.out.println("Opa, vc ja viu todas as historinhas. Deletando ip blocks...");
      ipStoryBlockRepository.deleteAll(ipStoryBlocks);
      return getRandomStory(requestIp);
    }

    Random rand = new Random();
    Story story = allowedStories.get(rand.nextInt(allowedStories.size()));

    IpStoryBlock newBlock = new IpStoryBlock(requestIp, story);
    ipStoryBlockRepository.save(newBlock);

    System.out.println("E a historinha escolhida foi: " + story.toString());

    System.out.println("|------------------------------------------|");

    return story;
  }

}
