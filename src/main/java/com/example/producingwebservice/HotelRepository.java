package com.example.producingwebservice;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baeldung.springsoap.gen.*;
import org.springframework.stereotype.Component;


@Component
public class HotelRepository {
 public Hotel ibiza;
   public List<BookList> bookLists;
    public Map<String , String> chambreMap;
    public Map<String , String> dateDepart;
    @PostConstruct
    public void initData() {
        ibiza = new Hotel();
        Adresse adresse = new Adresse();
        adresse.setPays("France");
        adresse.setVille("Paris");
        adresse.setRue("105 rue de claudio nougaro");
        adresse.setCodepostal(67100);
        ibiza.setNom("Ibiza");
        ibiza.setAdresse(adresse);

        ibiza.setEtoile(4);
        Chambre normaleUne = new Chambre();
        normaleUne.setNumero("0B");
        normaleUne.setDescription("c'est une chambre normale pour une personne");
        normaleUne.setDisponible(true);
        normaleUne.setType("Normale un lit");
        normaleUne.setPrix(45);
        normaleUne.setImage("https://pix6.agoda.net/hotelImages/210/2100662/2100662_17032117160051659350.jpg?s=1024x768");
        normaleUne.setPersonnes(1);

        Chambre normaleDeux = new Chambre();
        normaleDeux.setNumero("1B");
        normaleDeux.setDescription("c'est une chambre normale pour deux personne");
        normaleDeux.setDisponible(true);
        normaleDeux.setType("Normale deux lit");
        normaleDeux.setPrix(75);
        normaleDeux.setImage("https://pix6.agoda.net/hotelImages/210/2100662/2100662_17032117160051659350.jpg?s=1024x768");
        normaleDeux.setPersonnes(2);

        Chambre priveOne = new Chambre();
        priveOne.setNumero("2B");
        priveOne.setDescription("c'est une chambre vip pour une personne");
        priveOne.setDisponible(true);
        priveOne.setType("VIP un grand lit");
        priveOne.setPrix(150);
        priveOne.setImage("https://pix6.agoda.net/hotelImages/5668227/0/71f6f26a9884f5d8f8b805d3a5f7832b.jpg?s=1024x768");
        priveOne.setPersonnes(1);

        Chambre priveDeux = new Chambre();
        priveDeux.setNumero("2B");
        priveDeux.setDescription("c'est une chambre vip pour deux personne");
        priveDeux.setDisponible(true);
        priveDeux.setType("VIP deux grand lit");
        priveDeux.setPrix(300);
        priveDeux.setPersonnes(2);
        priveDeux.setImage("http://www.ggrasia.com/wp-content/uploads/2015/05/JW-Marriot-hotel-room-Galaxy-Macau-Phase-2-e1432637852679.jpg");
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(normaleUne);
        chambres.add(normaleDeux);
        chambres.add(priveOne);
        chambres.add(priveDeux);
        ibiza.setChambres(chambres);

    }
public GetReservationResponse reserver(GetReservationRequest request)
{
    GetReservationResponse response = new GetReservationResponse();
  String numeroDeChambre = chambreMap.get(request.getOffreId());
 String dateDep = dateDepart.get(request.getOffreId());

  for (BookList bookList: bookLists)
  {
      if (bookList.getOffreId().equals(request.getOffreId()))
      {
          for (Chambre c: ibiza.getChambres())
          {
              if (c.getNumero().equals(numeroDeChambre)) {
                  if (!c.isDisponible())
                  {
                      response.setConfirmation("désolé le chambre n'est pas disponible pour l'instant veuillez regardé " +
                              "la date disponible de cette offre ou choisir un autre offre");
                      return response;
                  }
                  c.setDisponible(false);
                  c.setDatedispo(dateDep);
              }
          }
          response.setConfirmation("Vous avez réservé votre chambre chez Ibiza hotel!");
          return response;
      }

  }
    response.setConfirmation("votre demarche n'a pas réussi il y a eu une erreur");
    return response;

}
    public List<BookList> findChambre(GetHotelRequest request) {
       bookLists = new ArrayList<>();
       chambreMap = new HashMap<>();
       dateDepart = new HashMap<>();
        int i=0;
        for (Chambre c: ibiza.getChambres())
        {
             if (c.getPersonnes() == request.getNombreP())
                {
                    BookList bookList = new BookList();
                    i++;
                    bookList.setOffreId(i + "B");
                    chambreMap.put(bookList.getOffreId(),c.getNumero());
                    dateDepart.put(bookList.getOffreId(),request.getDateD());
                bookList.setType(c.getType());
                if (c.isDisponible()) {
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date(System.currentTimeMillis());
                    bookList.setDateDispo(formatter.format(date));
                }
                else
                    bookList.setDateDispo(c.getDatedispo());
                bookList.setPrix(c.getPrix());
               bookList.setImage(c.getImage());
                bookLists.add(bookList);
                }

        }

        return bookLists;
    }

}