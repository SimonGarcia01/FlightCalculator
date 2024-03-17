/**
 * @author Simon Garcia
 * @since 2024-03-16
 */
 
package ui;

import java.util.Scanner;
import java.util.Arrays;

public class FlightCalculator {
	public static void main(String[] args) {
		Scanner sk = new Scanner(System.in);
		
		//CONSTANTS:
		
		final double XS_BASE_FEE = 175000.0;
		final double S_COM_PERCENT = 0.25;
		final double M_COM_PERCENT = 0.30;
		final double COST_PER_10KG = 50000.0;
		final double COST_PER_23KG = 100000.0;
		final double SPECIAL_SEAT = 15000.0;
		final double CHOOSE_SEAT = 50000.0;
		
		final int XS_MAX_WEIGHT = 79;
		final int S_MAX_WEIGHT = 89;
		final int M_MAX_WEIGHT = 112;
		final int XS_MIN_WEIGHT = 3;
		final int S_MIN_WEIGHT = 13;
		final int M_MIN_WEIGHT = 36;
		
		final String XS_INCLUDED = "Articulo personal y WebCheckin";
		final String S_INCLUDED = "Articulo personal, equipaje de mano (10kg), WebCheckin y acumulacion de millas";
		final String M_INCLUDED = "Articulo personal, equipaje de mano (10kg), equipaje de bodega (23kg), seleccion de asiento economy, WebCheckin y acumulacion de millas";
		
		//General variables:
		double[] save10flights = new double[10];
		double maxFlight = 0;
		
		//HERE STARTS THE PROCESS
		//variable to show the number of flights that have been looped
		int flightCounter = 1;
		//Variable to loop any amount of times until the user says "no"
		String continueFlights = " ";
		do{
			//totalFlightPrice and companion conter need to be restarted every flight
			double totalFlightPrice = 0;
			String[] names = new String[3];
			double[] individualPrice = new double[3];
			String companions = "";

			//Variable for companion counter loop = a single flight
			int companionCounter = 3;
			System.out.println("----------------------------------------------------------------------------------");
			System.out.println("Ingreso del vuelo " + flightCounter); 
			do{

				//Variables for weight suggestion
				int counterTicket = 0;
				int mSugWeight = 0;
				int mLugGoal = 0;
				int sSugWeight = 0;
				int sLugGoal = 0;
				int xsSugWeight = 0;
				int xsLugGoal = 0;

				System.out.print("Ingrese su nombre: ");
				String name = sk.nextLine();

				System.out.print("Ingrese el estimado del peso total de su equipaje: ");
				int lugEst = sk.nextInt();
				sk.nextLine();
			
				if (lugEst > M_MAX_WEIGHT) {
					System.out.println("Sus necesidades superan las capacidades ofrecidas por nosotros. ¡Hasta luego!");
					//Used to stop the program abbruptly
					System.exit(1);
				} else if (lugEst <= M_MAX_WEIGHT) {
					mSugWeight = M_MAX_WEIGHT - M_MIN_WEIGHT;
					mLugGoal = lugEst - M_MIN_WEIGHT;
					counterTicket ++;
				}

				if (lugEst > S_MAX_WEIGHT) {
					sSugWeight = 0;
				} else if (lugEst <= S_MAX_WEIGHT) {
					sSugWeight = S_MAX_WEIGHT - S_MIN_WEIGHT;
					sLugGoal = lugEst - S_MIN_WEIGHT;
					counterTicket ++;
				}

				if (lugEst > XS_MAX_WEIGHT) {
					xsSugWeight = 0;
				} else if (lugEst <= XS_MAX_WEIGHT) {
					xsSugWeight = XS_MAX_WEIGHT - XS_MIN_WEIGHT;
					xsLugGoal = lugEst - XS_MIN_WEIGHT;
					counterTicket ++;
				}

				//Variables for minimum luggade calculations
				int mBag23Min = 0;
				int mBag10Min = 0;
				int mBag23Left = 0;
				int mBag10Left = 0;

				int sBag23Min = 0;
				int sBag10Min = 0;
				int sBag23Left = 0;
				int sBag10Left = 0;

				int xsBag23Min = 0;
				int xsBag10Min = 0;
				int xsBag23Left = 0;
				int xsBag10Left = 0;

				int[] bagSubs = new int[5];

				if (counterTicket == 3) {
					bagSubs = Arrays.copyOf(minBagNeeded(mSugWeight, mLugGoal, counterTicket),5);

					mBag23Min = bagSubs[0];
					mBag10Min = bagSubs[1];
					mBag23Left = bagSubs[2];
					mBag10Left = bagSubs[3];
					counterTicket = bagSubs[4];

					bagSubs = Arrays.copyOf(minBagNeeded(sSugWeight, sLugGoal, counterTicket),5);

					sBag23Min = bagSubs[0];
					sBag10Min = bagSubs[1];
					sBag23Left = bagSubs[2];
					sBag10Left = bagSubs[3];
					counterTicket = bagSubs[4];

					bagSubs = Arrays.copyOf(minBagNeeded(xsSugWeight, xsLugGoal, counterTicket),5);

					xsBag23Min = bagSubs[0];
					xsBag10Min = bagSubs[1];
					xsBag23Left = bagSubs[2];
					xsBag10Left = bagSubs[3];
					counterTicket = bagSubs[4];

				} else if (counterTicket == 2){
					bagSubs = Arrays.copyOf(minBagNeeded(mSugWeight, mLugGoal, counterTicket),5);

					mBag23Min = bagSubs[0];
					mBag10Min = bagSubs[1];
					mBag23Left = bagSubs[2];
					mBag10Left = bagSubs[3];
					counterTicket = bagSubs[4];

					bagSubs = Arrays.copyOf(minBagNeeded(sSugWeight, sLugGoal, counterTicket),5);

					sBag23Min = bagSubs[0];
					sBag10Min = bagSubs[1];
					sBag23Left = bagSubs[2];
					sBag10Left = bagSubs[3];
					counterTicket = bagSubs[4];
				} else {
					bagSubs = Arrays.copyOf(minBagNeeded(mSugWeight, mLugGoal, counterTicket),5);

					mBag23Min = bagSubs[0];
					mBag10Min = bagSubs[1];
					mBag23Left = bagSubs[2];
					mBag10Left = bagSubs[3];
					counterTicket = bagSubs[4];
				}

				//Variables calculate the base fee
				double[] baseFeeXsSM = baseRate(XS_BASE_FEE, S_COM_PERCENT, M_COM_PERCENT);

				double xsBasePrice = baseFeeXsSM[0];
				double sBasePrice = baseFeeXsSM[1];
				double mBasePrice = baseFeeXsSM[2];

				//Variables to save the minimum price based on minimum luggage requirements

				double xsMinPrice = calcMinPrice(xsBasePrice, xsBag23Min, xsBag10Min, COST_PER_23KG, COST_PER_10KG);
				double sMinPrice = calcMinPrice(sBasePrice, sBag23Min, sBag10Min, COST_PER_23KG, COST_PER_10KG);
				double mMinPrice = calcMinPrice(mBasePrice, mBag23Min, mBag10Min, COST_PER_23KG, COST_PER_10KG);
				
				//ENDS PROCESS OF CALCULATING AND SUGGESTING TICKETTYPE

				//Process of chosing a bag and saving the result
				int pickedBag = bagChoice(sk, xsSugWeight, xsSugWeight, mSugWeight, xsMinPrice, sMinPrice, mMinPrice, 
				XS_INCLUDED, S_INCLUDED, M_INCLUDED, xsBag23Min, xsBag10Min, sBag23Min, sBag10Min, 
				mBag23Min, mBag10Min);

				//Variables and process to add the addtionals to the final price
				String specialSeat = " ";
				String seatChoice = " ";
				double totalPrice = 0;

				if (pickedBag == 3) {
					System.out.println("El puesto M ya incluye elegir una silla economy.");

					specialSeat = yesOrNo(sk, "¿Desea elegir un asiento en el pasillo o la ventana?(Si/No): ", 
					"Escriba \"Si\" o \"No\". ");

					if(specialSeat.equalsIgnoreCase("si")){
						totalPrice = mMinPrice + SPECIAL_SEAT;
					} else {
						totalPrice = mMinPrice;
					}

					totalPrice = addExtraBags(sk, mBag10Left, mBag23Left, COST_PER_10KG, COST_PER_23KG, totalPrice);

				} else if  (pickedBag == 2) {
					System.out.println("El puesto S no viene con eleccion de asiento economy.");

					seatChoice = yesOrNo(sk, "¿Desea elegir su puesto?(Si/No): ", 
					"Escriba \"Si\" o \"No\". ");

					if(seatChoice.equalsIgnoreCase("si")){
						totalPrice = sMinPrice + CHOOSE_SEAT;
						
						//If the user wants to pick his seat, then he gets to decide if he wants a special one
						
						specialSeat = yesOrNo(sk, "¿Desea elegir un asiento en el pasillo o la ventana?(Si/No): ", 
						"Escriba \"Si\" o \"No\". ");	
			
						if(specialSeat.equalsIgnoreCase("si")){
							totalPrice = totalPrice + SPECIAL_SEAT;
						}
					} else {
						totalPrice = sMinPrice;
					}
					
					totalPrice = addExtraBags(sk, sBag10Left, sBag23Left, COST_PER_10KG, COST_PER_23KG, totalPrice);

				} else {
					System.out.println("El puesto XS no viene con eleccion de asiento economy.");

					seatChoice = yesOrNo(sk, "¿Desea elegir su puesto?(Si/No): ", 
					"Escriba \"Si\" o \"No\". ");


					if(seatChoice.equalsIgnoreCase("si")){
						totalPrice = xsMinPrice + CHOOSE_SEAT;
						
						//If the user wants to pick his seat, then he gets to decide if he wants a special one
						specialSeat = yesOrNo(sk, "¿Desea elegir un asiento en el pasillo o la ventana?(Si/No): ", 
					"Escriba \"Si\" o \"No\". ");
			
						if(specialSeat.equalsIgnoreCase("si")){
							totalPrice = totalPrice + SPECIAL_SEAT;

						}
					} else {
						totalPrice = xsMinPrice;
					}

					totalPrice = addExtraBags(sk, xsBag10Left, xsBag23Left, COST_PER_10KG, COST_PER_23KG, totalPrice);
				}
			
				names[3-companionCounter] = name;
				individualPrice[3-companionCounter] = totalPrice;


				System.out.println("El total de " + name + " es de $" + totalPrice);
			
				//To add to the total cost of the flight
				totalFlightPrice = totalPrice + totalFlightPrice;
				//To loop in case there are companions and generate the right bill
				if (companionCounter == 3) {
					companions = yesOrNo(sk, "¿Has traido acompañantes? (Si/No): ", 
					"Escriba \"Si\" o \"No\". ");

					if (companions.equalsIgnoreCase("si")) {
						companionCounter--;
					} else {
						companionCounter = 0;
					}
				} else {
					companionCounter--;
				}
			}while(companionCounter !=0);
			
			
			for(int n =9; n != 0; n--) {
				save10flights[n] = save10flights[n-1];
			}

			save10flights[0] = totalFlightPrice;
			
			System.out.println("----------------------------------------------------------------------------------");
			if(companions.equalsIgnoreCase("si")) {
				System.out.println("Precios individuales por cada persona");
				for(int n = 0; n <= 2; n++) {
					System.out.println(names[n] + " ------------- $ " + individualPrice[n]);
				}
				System.out.println("El total de este vuelo fue de: $" + totalFlightPrice);
			} else {
				System.out.println("El total de su vuelo, " + names[0] + ", es de $" + individualPrice[0]);
			}
			System.out.println("----------------------------------------------------------------------------------");


			//saving the max after every loop
			if(flightCounter == 1) {
				maxFlight = totalFlightPrice;
			} else {
				if(totalFlightPrice >= maxFlight){
					maxFlight = totalFlightPrice;
				}
			}

			flightCounter++;

			continueFlights = yesOrNo(sk, "Desea agregar otro vuelo? (Si/No): ","Escriba \"Si\" o \"No\". " );

		}while(continueFlights.equalsIgnoreCase("si"));

		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("\nEl precio mas alto calculado de los ultimos vuelos es de $" + maxFlight);
		System.out.println("----------------------------------------------------------------------------------");
		
		//To check the total price of one of the ten saved flights
		System.out.print("Ingrese el numero de vuelo (de los ultimos 10 ingresos) que desea ver su precio. Va desde: ");
		System.out.print("\n\t1: Ultimo vuelo de los ultimos 10 vuelos\n\t\tHasta\n\t10: vuelo mas reciente de los ultimos 10 vuelos)\nIngrese la pocision: ");
		int checkPriceFlight = sk.nextInt();
		sk.nextLine();

		System.out.println("El valor del vuelo "+ checkPriceFlight+ " es de $" + save10flights[checkPriceFlight-1]);
		System.out.println("----------------------------------------------------------------------------------");
		sk.close();
	}

	/**
	 * <p><b>calcMinPrice</b></p>
	* <b>Description:</b> The method calculates the minimum price based on the minimum amount of luggage that is required to cover the needs of the user's estimated baggage weight additional to the base fee provided.
	*	
	* <p><b>Preconditions:</b></p>
	* <ul>
	* 	<li> {@code basePrice} should be a double greater than 0. </li>
	*	<li>{@code minBag23} and {@code min10Bag} should be int greater than 0. </li>
	*	<li> {@code costPer23} and {@code costPer10} should be double greater than 0 less than 1. </li>
	* </ul>
	*
	* <p><b>Postconditions:</b></p>
	* <ul>
	* 	<li>The additional cost per bag of 23 kg and 10kg is added to the base price that is provided.  </li>
	* </ul>
	*
	* @param basePrice The base fee for the XS, S or M ticket.
	* @param min23Bag The minimum number of 23 kg bags needed to cover the baggage weight.
	* @param min10Bag The minimum number of 10 kg bags needed to cover the baggage weight.
	* @param costPer23 The unitary cost for every 23 kg bag.
	* @param costPer10 The unitary cost for every 10kg bag.
	* @return The calculated minimum price.
	*/

	public static double calcMinPrice(double basePrice, int min23Bag, int min10Bag, 
	double costPer23, double costPer10) {
		double minPrice = basePrice + (min23Bag * costPer23) + (min10Bag * costPer10);
		return minPrice;
	}
	
	/**
	 * <p><b>baseRate</b></p>
	* <b>Description:</b> The method calculates the basic fee for the XS, S and M ticket using the XS fee as the base and adding the extra percentage for the fee
	*
	* <p><b>Preconditions:</b></p>
	* <ul>
	*	<li> {@code XsRate} should be a double greater than 0. </li>
	* 	<li> {@code sPercent} and {@code mPercent} should be double greater than 0 less than 1. </li>
	* </ul>
	*
	* <p><b>Postconditions:</b></p>
	* <ul>
	*	<li>The basic fee for XS, S and M is calculated and saved as a double in an array containing the three prices.  </li>
	* </ul>
	*
	* @param xsRate The minimum fee for the XS ticket
	* @param sPercent The additional percentage that costs the S ticket compared to the XS ticket
	* @param mPercent The additional percentage that costs the M ticket compared to the XS ticket
	* @return The most basic price for every ticket type
	*/

	public static double[] baseRate(double xsRate, double sPercent, double mPercent) {
		double sFee = xsRate * (1 + sPercent);
		double mFee = xsRate * (1 + mPercent);
		double[] baseFee = {xsRate, sFee, mFee};
		
		return baseFee;
	}
	
	/**
	 * <p><b>minBagNeeded</b></p>
	* <b>Description:</b> The method calculates the minimum amount of bags needed to fullfill the user's estimated luggage weight. It continually loops and continually reduces the max weight given in a ticket type
	* taking into account the difference between the weight estimate and the max weight (to decide whether it will reduce 23 kg or 10kg). If the goal weight is less than 0 (after the included bag weight is 
	* is subtracted) then it will save the user needs 0 additional bags and every bag is available for later use. The calc counter is to further loop and do this process on every ticket type (XS, S and M).
	*
	*
	* <p><b>Preconditions:</b></p>
	* <ul>
	*	<li> {@code weightMatch} should be an int greater than 0. It has to be the maximum weight that the user can carry only in additional luggage (ignoring what the ticket includes).</li>
	* 	<li> {@code goalWeight} should be an int. It can be negative. To use {@code goalWeight}, the luggage weight included in a ticker should be subracted from the luggage estimate given by the user.
	* it cannot be greater than the {@code weightMatch}. </li>
	*	<li> {@code calcCounter} should be an int greater than 0 but less or equal to 3. Its used to identify every ticket Type along the calculations. <i>3 = M, S, XS are available. 2 = M and S are
	* available. 1 = M is available </i> </li>
	* </ul>
	*
	* <p><b>Postconditions:</b></p>
	* <ul>
	*	<li>The minimum baggage and the left over baggage is saved inside an array. The array also holds --calcounter to afterwards do the same calculatio with other ticker types. </li>
	* </ul>
	*
	* @param weightMatch The maximum weight that can be held with additional luggage. It will be reduced along the loops.
	* @param goalWeight The estimated baggage weight given the by the user minus the "free" weight that comes with a ticket type.
	* @param calcCounter The identifier for the secuential processes to calculate the {@code minBagNeeded} for every ticket type.
	* @return An array that holds the minimum bags needed, the ones left after the process and the ticker identifier.
	*/

	public static int[] minBagNeeded(int weightMatch, int goalWeight, int calcCounter) {
		int bag10kgCounter = 3;
		int bag23kgCounter = 2;
		int[] bagMin = {0, 0, 0, 0, 0};
		
		if (goalWeight >= 0) {
			while ((weightMatch - goalWeight)>=0) {
				if (weightMatch - goalWeight >= 23 && bag23kgCounter > 0) {
					weightMatch -= 23;
					bag23kgCounter--;
				} else if (weightMatch - goalWeight >= 10 && bag10kgCounter > 0) {
					weightMatch -= 10;
					bag10kgCounter--;
				} else {
					break; 
				}
			}
			
			bagMin[0] = bag23kgCounter;
			bagMin[1] = bag10kgCounter;
			bagMin[2] = 2 - bag23kgCounter;
			bagMin[3] = 3 - bag10kgCounter;
			bagMin[4] = --calcCounter;
		} else {
			bagMin[0] = 0;
			bagMin[1] = 0;
			bagMin[2] = 2;
			bagMin[3] = 3;
			bagMin[4] = --calcCounter;
		}
		return bagMin;
	}
	
	/**
	 * <p><b>bagChoice</b></p>
	* <b>Description:</b> Taking into consideration which ticket type can cover the users needs, this method will print and available tickets with the information every ticket. After the user decides which ticket
	* he wants, it will save and return the choice for later use.
	*
	* <p><b>Preconditions:</b></p>
	* <ul>
	*	<li> {@code sk} Should be the name of the available Scanner in Main.</li>
	* 	<li> {@code xsSug}, {@code sSug} and {@code mSug} should be an int equal to 0 or the Maxweight available to a ticket - minimum weight carried by a ticket (comes "free" with ticket). </li>
	*	<li> {@code xsPrice}, {@code sPrice} and {@code mPrice} should be a double that holds the price that is calculated by {@link #calcMinPrice}. This takes into consideration the minimum 
	*	bag needed ({@link #minBagNeeded}).</li>
	* 	<li> {@code xsInclude}, {@code sInclude} and {@code mInclude} should be string the contain the information of what the ticket includes. </li>
	* 	<li> {@code xs23BagMin}, {@code xs10BagMin}, {@code s23BagMin}, {@code s10BagMin}, {@code m23BagMin} and {@code m10BagMin} should be int greater or equal to 0 that hold how many bags are needed for every
	* ticket to cover the users weight need. This are all calculated by {@link #minBagNeeded}</li>
	* </ul>
	*
	* <p><b>Postconditions:</b></p>
	* <ul>
	*	<li> It displays all the necessary information that the user needs to choose one of the ticket types. Then it will ask for their choice and check if the choise is inside of the available options. </li>
	* </ul>
	*
	* @param sk The scanner used in main
	* @param xsSug The weight suggested by the system for XS ticket {@link #minBagNeeded}
	* @param sSug The weight suggested by the system for S ticket {@link #minBagNeeded}
	* @param mSug The weight suggested by the system for M ticket {@link #minBagNeeded}
	* @param xsPrice The minimum price for XS ticket that covers weight needs {@link #calcMinPrice}
	* @param sPrice The minimum price for S ticket that covers weight needs {@link #calcMinPrice}
	* @param mPrice The minimum price for M ticket that covers weight needs {@link #calcMinPrice}
	* @param xsInclude The informatation of everything the XS ticket includes
	* @param sInclude The information of everything the S ticket includes
	* @param mInclude The information of everything the M ticket includes
	* @param xs23BagMin The minimum number of 23kg bag needed to cover users needs with the XS ticket {@link #minBagNeeded}
	* @param xs10BagMin The minimum number of 10kg bag needed to cover users needs with the XS ticket {@link #minBagNeeded}
	* @param s23BagMin The minimum number of 23kg bag needed to cover users needs with the S ticket {@link #minBagNeeded}
	* @param s10BagMin The minimum number of 10kg bag needed to cover users needs with the S ticket {@link #minBagNeeded}
	* @param m23BagMin The minimum number of 23kg bag needed to cover users needs with the M ticket{@link #minBagNeeded}
	* @param m10BagMin The minimum number of 10kg bag needed to cover users needs with the M ticket {@link #minBagNeeded}
	* @return shows the selected ticket type by the user. <i> 3 = M, 2 = S, 1 = XS </i>
	*/

	public static int bagChoice(Scanner sk, int xsSug, int sSug, int mSug, double xsPrice, double sPrice, 
	double mPrice, String xsInclude, String sInclude, String mInclude, int xs23BagMin, int xs10BagMin, 
	int s23BagMin, int s10BagMin, int m23BagMin, int m10BagMin) {
		int finalChoice = 0;
		String chooBag = "m";

		if (xsSug != 0) {
			
			System.out.println("Segun el peso ingresado, le sugerimos las siguientes 3 opciones: ");
			System.out.println("\t-Tarifa XS con " + xs23BagMin + " maletas de 23kg y " + xs10BagMin + " maletas de 10kg por un precio de $" + xsPrice + ". De base incluye: " + xsInclude);
			System.out.println("\t-Tarifa S con " + s23BagMin + " maletas de 23kg y " + s10BagMin + " maletas de 10kg por un precio de $" + sPrice + ". De base incluye: " + sInclude);
			System.out.println("\t-Tarifa M con " + m23BagMin + " maletas de 23kg y " + m10BagMin + " maletas de 10kg por un precio de $" + mPrice + ". De base incluye: " + mInclude);
			
			System.out.print("Ingrese cual tarifa desea(XS/S/M): ");
			chooBag = sk.nextLine();
			
			while(!(chooBag.equalsIgnoreCase("m") || chooBag.equalsIgnoreCase("s") || chooBag.equalsIgnoreCase("xs"))) {
				System.out.print("Los unicos valores validos son: XS, S y M. \nIngrese cual tarifa desea: ");
				chooBag = sk.nextLine();
			}
			
			if (chooBag.equalsIgnoreCase("m")){
				finalChoice = 3;
			} else if (chooBag.equalsIgnoreCase("s")) {
				finalChoice = 2;
			} else {
				finalChoice = 1;
			}
		} else if (sSug != 0) {
			System.out.println("Segun el peso ingresado, le sugerimos las siguientes 2 opciones: ");
			System.out.println("\t-Tarifa S con " + s23BagMin + " maletas de 23kg y " + s10BagMin + " maletas de 10kg por un precio de $" + sPrice + ". De base incluye: " + sInclude); 
			System.out.println("\t-Tarifa M con " + m23BagMin + " maletas de 23kg y " + m10BagMin + " maletas de 10kg por un precio de $" + mPrice + ". De base incluye: " + mInclude);
			
			System.out.print("Ingrese cual tarifa desea(S/M): ");
			chooBag = sk.nextLine();
			
			while(!(chooBag.equalsIgnoreCase("m") || chooBag.equalsIgnoreCase("s"))) {
				System.out.print("Los unicos valores validos son: S y M. \nIngrese cual tarifa desea: ");
				chooBag = sk.nextLine();
			}
			
			if (chooBag.equalsIgnoreCase("m")){
				finalChoice = 3;
			} else {
				finalChoice = 2;
			}
			
		} else {
			System.out.println("Segun el peso ingresado, el unico valor que cubre sus necesidades es la M:");
			System.out.println("\t-Tarifa M con " + m23BagMin + " maletas de 23kg y " + m10BagMin + " maletas de 10kg por un precio de $" + mPrice + ". De base incluye: " + mInclude);
			
			System.out.print("Por favor, ingrese esta tarifa (M): ");
			chooBag = sk.nextLine();
			
			while(! chooBag.equalsIgnoreCase("m")) {
				System.out.print("El unico valor valido es la tarifa M. Ingresela: ");
				chooBag = sk.nextLine();
			}
			
			finalChoice = 3;
		}
		
		return finalChoice;
	}
	
	/**
	 * <p><b>addExtraBags</b></p>
	* <b>Description:</b> The method will display how many bags the user has left and then ask how many of the 10kg and/or 23kg luggage he wants (if available). Before adding any number of the left bags
	* {@link #minBagNeeded} it will make sure that the user actually wants the additional bags or not ({@link #yesOrNo}). After the number of bags are chosen, each is multiplied by the unitary cost and added.
	* If there aren't any 10kg or 23kg bags left to choose, this process will be skipped and the total price will be unchanged. 
	*
	* <p><b>Preconditions:</b></p>
	* <ul>
	*	<li> {@code sk} Should be the name of the available Scanner in Main.</li>
	* 	<li> {@code left10kgBag} amd {@code left10kgBag} should be int greater or equal to 0 that hold how many bags are available after the {@link #minBagNeeded} calculations. </li>
	* 	<li> {@code costPer10Kg} and {@code costPer23Kg} shoud be a double that holds the unitary price for every 10kg and 23kg bag respectively </li>
	* 	<li> {@code bagLastPrice} should be a double greater than 0 that holds the price of the ticket and minimum luggage requirements {@link #calcMinPrice} .</li>
	* </ul>
	*
	* <p><b>Postconditions:</b></p>
	* <ul>
	* 	<li> It displays all the information to choose if the user wants additional bags and calculates the cost of this additional bags. In case there are no bags available it will skip the step and save 0s. </li>
	* </ul>
	* 
	* @param sk The scanner used in main
	* @param left10KgBag The number of 10kg bag left after calculating the minimum needed to cover users needs (depends of the ticket type) {@link #minBagNeeded}
	* @param left23KgBag The number of 23kg bag left after calculating the minimum needed to cover users needs (depends of the ticket type) {@link #minBagNeeded}
	* @param costPer10Kg The cost per 10kg bag
	* @param costPer23Kg The cost per 23kg bag
	* @param bagLastPrice The cost that takes into account the additionals added by minimum fee, adittional fee per bags, and seat choices
	* @return The total price after adding the additional bags
	*/

	public static double addExtraBags(Scanner sk, int left10KgBag, int left23KgBag, double costPer10Kg,
	double costPer23Kg, double bagLastPrice) {
		
		int numAdd10 = 0;
		int numAdd23 = 0;
		String add23Bag = " ";
		String add10Bag = " ";

		if (left23KgBag != 0) {
			
			add23Bag = yesOrNo(sk,"¿Desea agregar mas maletas de 23kg(Si/No?): " , "Porfavor, \"Si\" o \"No\"");
			
			if(add23Bag.equalsIgnoreCase("si")) {
				do{
					System.out.print("¿Cuantas maletas de 23kg desea agregar? (Quedan " + left23KgBag 
					+ " disponibles): ");
					numAdd23 = sk.nextInt();
					sk.nextLine();

					if (numAdd23 <= left23KgBag && numAdd23 >= 0) {
						break;
					} else {
						System.out.printf("Tiene %d disponibles. No ingrese negativos.%n", 
						left23KgBag);
					}
				} while(true);
			} else {
				numAdd23 = 0;
			}

			if (left10KgBag !=0) {
				
				add10Bag = yesOrNo(sk,"¿Desea agregar mas maletas de 10kg(Si/No?): " , "Porfavor, \"Si\" o \"No\"");
				
				if(add10Bag.equalsIgnoreCase("si")) {
					do{
						System.out.print("¿Cuantas maletas de 10kg desea agregar? (Quedan " + left10KgBag 
						+ " disponibles): ");
						numAdd10 = sk.nextInt();
						sk.nextLine();
	
						if (numAdd10 <= left10KgBag && numAdd10 >= 0) {
							break;
						} else {
							System.out.printf("Tiene %d disponibles. No ingrese negativos.", 
							left10KgBag);
						}
					} while(true);
				} else {
					numAdd10 = 0;
				}
			}
		} else if (left10KgBag !=0) {
			System.out.print("No tiene maletas de 23kg adicionales disponibles, pero si de 10kg.");
			
			add10Bag = yesOrNo(sk,"¿Desea agregar mas maletas de 10kg(Si/No?): " , "Porfavor, \"Si\" o \"No\"");
			
			if(add10Bag.equalsIgnoreCase("si")) {
				do{
					System.out.print("¿Cuantas maletas de 10kg desea agregar? (Quedan " + left10KgBag 
					+ " disponibles): ");
					numAdd10 = sk.nextInt();
					sk.nextLine();

					if (numAdd10 <= left10KgBag && numAdd10 >= 0) {
						break;
					} else {
						System.out.printf("Tiene %d disponibles. No ingrese negativos.", 
						left10KgBag);
					}
				} while(true);
			} else {
				numAdd10 = 0;
			}
		} else {
			System.out.println("No tiene maletas ni de 23 ni 10 kg disponible.");
			numAdd10 = 0;
			numAdd23 = 0;
		}
		
		double totalAddBags = bagLastPrice + (numAdd23*costPer23Kg) + (numAdd10*costPer10Kg);

		return totalAddBags;
	}
	
	/**
	 * <p><b>addExtraBags</b></p>
	* <b>Description:</b> The method creates a loop that verifies that the only valid input is "yes" or "no" and the returns the validated input. 
	*
	* <p><b>Preconditions:</b></p>
	* <ul>
	*	<li> {@code sk} Should be the name of the available Scanner in Main.</li>
	* 	<li> {@code textBeforeCheck} A string that contains a "yes" or "no" question .</li>
	* <li> {@code textTryAgain} A string that the answer is either "yes" or "no".</li>
	* </ul>
	*
	* <p><b>Postconditions:</b></p>
	* <ul>
	* 	<li> It will save a string the "yes" or "no" after being validated is either of those options </li>
	* </ul>
	* 
	* @param sk The scanner used in main
	* @param textBeforeCheck Text message that asks the yes or no questio
	* @param textTryAgain Text message that appears if the input is neither "yes" or "no"
	* @return "yes" or "no" depending on the users choice
	*/

	public static String yesOrNo(Scanner sk, String textBeforeCheck, String textTryAgain){

		String checkedYesOrNo = " ";

		do{
				
			System.out.print(textBeforeCheck);
			checkedYesOrNo = sk.nextLine();
			
			if(checkedYesOrNo.equalsIgnoreCase("si") || checkedYesOrNo.equalsIgnoreCase("no")) {
				break;
			}

			System.out.println(textTryAgain);

		}while(true);

		return checkedYesOrNo;
	}
}