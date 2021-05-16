package leao.historinhas.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import leao.historinhas.models.Moderator;
import leao.historinhas.models.Story;
import leao.historinhas.models.StoryStatus;
import leao.historinhas.repositories.ModeratorRepository;
import leao.historinhas.repositories.StoryRepository;

@Component
public class BootStrapData implements CommandLineRunner{

  private final StoryRepository storyRepository;
  private final ModeratorRepository moderatorRepository;

  public BootStrapData(StoryRepository storyRepository, ModeratorRepository moderatorRepository){
    this.storyRepository = storyRepository;
    this.moderatorRepository = moderatorRepository;
  }

  @Override
  public void run(String... args) throws Exception {

    Story firstStory = new Story("ontem eu estava andando de " +
    "bicicleta e do nada apareceu um menino de bicicleta " +
    "que começou a correr mais rápido do que eu. aí eu " +
    "comecei a apostar corrida com ele, e não sei se ele " +
    "sabe, mas eu venci.", "jhleao99@gmail.com", StoryStatus.APPROVED);

    Story secondStory = new Story("hoje é o segundo dia em que eu " + 
    "pego exatamente 300g de almoço no restaurante.", "jhleao99@gmail.com", 
    StoryStatus.APPROVED);

    Story thirdStory = new Story("saí da empresa para atender um telefonema, " +
    "mas deixei a porta escorada para poder entrar depois. alguém entrou " + 
    "antes de mim e fechou a porta.", "jhleao99@gmail.com", 
    StoryStatus.PENDING);

    storyRepository.save(firstStory);
    storyRepository.save(secondStory);
    storyRepository.save(thirdStory);

    Moderator firstModerator = new Moderator("jhleao", "senha");

    moderatorRepository.save(firstModerator);

    System.out.println("Bootstrap inicializado!");
    System.out.println("Número de historinhas: " + storyRepository.count());
    System.out.println("Número de moderadores: " + moderatorRepository.count());

  }
  
}
