package expiryeliminator.parser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import expiryeliminator.commands.AddIngredientCommand;
import expiryeliminator.commands.AddRecipeCommand;
import expiryeliminator.commands.ByeCommand;
import expiryeliminator.commands.Command;
import expiryeliminator.commands.DecrementCommand;
import expiryeliminator.commands.DeleteIngredientCommand;
import expiryeliminator.commands.DeleteRecipeCommand;
import expiryeliminator.commands.HelpCommand;
import expiryeliminator.commands.IncorrectCommand;
import expiryeliminator.commands.IncrementCommand;
import expiryeliminator.commands.ListCommand;
import expiryeliminator.commands.ListIngredientExpiringCommand;
import expiryeliminator.commands.ListIngredientsExpiredCommand;
import expiryeliminator.commands.ListRecipeCommand;
import expiryeliminator.commands.ViewIngredientCommand;
import expiryeliminator.commands.ViewRecipeCommand;
import expiryeliminator.commands.UpdateRecipeCommand;
import expiryeliminator.data.Ingredient;
import expiryeliminator.data.IngredientList;
import expiryeliminator.data.exception.DuplicateDataException;
import expiryeliminator.parser.argparser.ExpiryDateParser;
import expiryeliminator.parser.argparser.IngredientParser;
import expiryeliminator.parser.argparser.QuantityParser;
import expiryeliminator.parser.argparser.RecipeParser;
import expiryeliminator.parser.exception.InvalidArgFormatException;
import expiryeliminator.parser.exception.InvalidPrefixException;
import expiryeliminator.parser.exception.MissingPrefixException;
import expiryeliminator.parser.exception.MultipleArgsException;
import expiryeliminator.parser.prefix.MultipleArgPrefix;
import expiryeliminator.parser.prefix.SingleArgPrefix;


/**
 * Parses user input.
 */
public class Parser {
    //@@author bernardboey-reused
    // Reused from https://github.com/bernardboey/ip/blob/master/src/main/java/duke/parser/Parser.java
    // with minor modifications

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("^(?<command>[^/]+)(?<args> .*)?$");

    private static final SingleArgPrefix PREFIX_RECIPE = new SingleArgPrefix("r");
    private static final SingleArgPrefix PREFIX_INGREDIENT = new SingleArgPrefix("i");
    private static final SingleArgPrefix PREFIX_QUANTITY = new SingleArgPrefix("q");
    private static final SingleArgPrefix PREFIX_EXPIRY = new SingleArgPrefix("e");
    private static final MultipleArgPrefix PREFIX_MULTIPLE_INGREDIENT = new MultipleArgPrefix(PREFIX_INGREDIENT);
    private static final MultipleArgPrefix PREFIX_MULTIPLE_QUANTITY = new MultipleArgPrefix(PREFIX_QUANTITY);

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_INVALID_ARGUMENT_FORMAT = "Invalid argument format!\n%1$s";
    private static final String MESSAGE_UNRECOGNISED_COMMAND = "I'm sorry, but I don't know what that means :-(\n"
            + "For help, please type `help`.";

    /**
     * Parses user input as a command.
     *
     * @param userInput Input command together with any arguments.
     * @return Command that corresponds to the user input, if valid.
     */
    public static Command parseCommand(String userInput) {
        assert userInput != null : "User input string cannot be null";
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput);
        if (!matcher.matches()) {
            throw new RuntimeException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
        final String command = matcher.group("command");
        String args = matcher.group("args");

        try {
            switch (command) {
                case AddIngredientCommand.COMMAND_WORD:
                    return prepareAddIngredient(args);
                case DecrementCommand.COMMAND_WORD:
                    return prepareDecrementIngredient(args);
                case IncrementCommand.COMMAND_WORD:
                    return prepareIncrementIngredient(args);
                case DeleteIngredientCommand.COMMAND_WORD:
                    return prepareDeleteIngredient(args);
                case ListCommand.COMMAND_WORD:
                    return new ListCommand();
                case ListIngredientExpiringCommand.COMMAND_WORD:
                    return new ListIngredientExpiringCommand();
                case ListIngredientsExpiredCommand.COMMAND_WORD:
                    return new ListIngredientsExpiredCommand();
                case ViewIngredientCommand.COMMAND_WORD:
                    return prepareViewIngredient(args);
                case AddRecipeCommand.COMMAND_WORD:
                    return prepareAddRecipe(args);
                case DeleteRecipeCommand.COMMAND_WORD:
                    return prepareDeleteRecipe(args);
                case ListRecipeCommand.COMMAND_WORD:
                    return new ListRecipeCommand();
                case ViewRecipeCommand.COMMAND_WORD:
                    return prepareViewRecipe(args);
                case UpdateRecipeCommand.COMMAND_WORD:
                    return prepareUpdateRecipe(args);
                case ByeCommand.COMMAND_WORD:
                    return new ByeCommand();
                case HelpCommand.COMMAND_WORD:
                    return new HelpCommand();
                default:
                    return new IncorrectCommand(MESSAGE_UNRECOGNISED_COMMAND);
            }
        } catch (InvalidArgFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_ARGUMENT_FORMAT, e.getMessage()));
        }

    }
    //@@author

    private static Command prepareAddIngredient(String args) throws InvalidArgFormatException {
        final ArgParser argParser = new ArgParser(PREFIX_INGREDIENT, PREFIX_QUANTITY, PREFIX_EXPIRY);
        try {
            argParser.parse(args);
        } catch (InvalidPrefixException | MissingPrefixException | MultipleArgsException e) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIngredientCommand.MESSAGE_USAGE));
        }

        final String ingredient = new IngredientParser().parse(argParser.getSingleArg(PREFIX_INGREDIENT));
        final int quantity = new QuantityParser().parse(argParser.getSingleArg(PREFIX_QUANTITY));
        final LocalDate expiry = new ExpiryDateParser().parse(argParser.getSingleArg(PREFIX_EXPIRY));
        return new AddIngredientCommand(ingredient, quantity, expiry);
    }

    private static Command prepareDecrementIngredient(String args) throws InvalidArgFormatException {
        final ArgParser argParser = new ArgParser(PREFIX_INGREDIENT, PREFIX_QUANTITY);
        try {
            argParser.parse(args);
        } catch (InvalidPrefixException | MissingPrefixException | MultipleArgsException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DecrementCommand.MESSAGE_USAGE));
        }

        final String ingredient = new IngredientParser().parse(argParser.getSingleArg(PREFIX_INGREDIENT));
        final int quantity = new QuantityParser().parse(argParser.getSingleArg(PREFIX_QUANTITY));
        return new DecrementCommand(ingredient, quantity);
    }

    private static Command prepareIncrementIngredient(String args) throws InvalidArgFormatException {
        final ArgParser argParser = new ArgParser(PREFIX_INGREDIENT, PREFIX_QUANTITY);
        try {
            argParser.parse(args);
        } catch (InvalidPrefixException | MissingPrefixException | MultipleArgsException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncrementCommand.MESSAGE_USAGE));
        }

        final String ingredient = new IngredientParser().parse(argParser.getSingleArg(PREFIX_INGREDIENT));
        final int quantity = new QuantityParser().parse(argParser.getSingleArg(PREFIX_QUANTITY));
        return new IncrementCommand(ingredient, quantity);
    }

    private static Command prepareDeleteIngredient(String args) throws InvalidArgFormatException {
        final ArgParser argParser = new ArgParser(PREFIX_INGREDIENT);
        try {
            argParser.parse(args);
        } catch (InvalidPrefixException | MissingPrefixException | MultipleArgsException e) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteIngredientCommand.MESSAGE_USAGE));
        }

        final String ingredient = new IngredientParser().parse(argParser.getSingleArg(PREFIX_INGREDIENT));
        return new DeleteIngredientCommand(ingredient);
    }

    /**
     * Creates a AddRecipeCommand from the inputs.
     *
     * @param args Command arguments.
     * @return a AddRecipeCommand with the recipe name and the ingredients if successful
     * and an IncorrectCommand if not.
     */
    private static Command prepareAddRecipe(String args) throws InvalidArgFormatException {
        final ArgParser argParser = new ArgParser(PREFIX_RECIPE, PREFIX_MULTIPLE_INGREDIENT, PREFIX_MULTIPLE_QUANTITY);
        try {
            argParser.parse(args);
        } catch (InvalidPrefixException | MissingPrefixException | MultipleArgsException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRecipeCommand.MESSAGE_USAGE));
        }

        final String recipe = new RecipeParser().parse(argParser.getSingleArg(PREFIX_RECIPE));
        final ArrayList<String> ingredients =
                new IngredientParser().parse(argParser.getArgList(PREFIX_MULTIPLE_INGREDIENT));
        final ArrayList<Integer> quantities =
                new QuantityParser().parse(argParser.getArgList(PREFIX_MULTIPLE_QUANTITY));
        final IngredientList ingredientList = new IngredientList();
        final IncorrectCommand error = addIngredients(ingredients, quantities, ingredientList);
        if (error != null) {
            return error;
        }
        assert !recipe.isBlank();
        return new AddRecipeCommand(recipe, ingredientList);
    }

    private static Command prepareUpdateRecipe(String args) throws InvalidArgFormatException {
        final ArgParser argParser = new ArgParser(PREFIX_RECIPE, PREFIX_MULTIPLE_INGREDIENT, PREFIX_MULTIPLE_QUANTITY);
        try {
            argParser.parse(args);
        } catch (InvalidPrefixException | MissingPrefixException | MultipleArgsException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateRecipeCommand.MESSAGE_USAGE));
        }

        final String recipe = new RecipeParser().parse(argParser.getSingleArg(PREFIX_RECIPE));
        final ArrayList<String> ingredients =
                new IngredientParser().parse(argParser.getArgList(PREFIX_MULTIPLE_INGREDIENT));
        final ArrayList<Integer> quantities =
                new QuantityParser().parse(argParser.getArgList(PREFIX_MULTIPLE_QUANTITY));
        final IngredientList ingredientList = new IngredientList();
        final IncorrectCommand error = addIngredients(ingredients, quantities, ingredientList);
        if (error != null) {
            return error;
        }
        assert !recipe.isBlank();
        return new UpdateRecipeCommand(recipe, ingredientList);
    }

    /**
     * Creates a DeleteRecipeCommand from the inputs.
     *
     * @param args Command arguments.
     * @return a DeleteRecipeCommand with the recipe name if successful and an IncorrectCommand if not.
     */
    private static Command prepareDeleteRecipe(String args) throws InvalidArgFormatException {
        final ArgParser argParser = new ArgParser(PREFIX_RECIPE);
        try {
            argParser.parse(args);
        } catch (InvalidPrefixException | MissingPrefixException | MultipleArgsException e) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRecipeCommand.MESSAGE_USAGE));
        }

        final String recipe = new RecipeParser().parse(argParser.getSingleArg(PREFIX_RECIPE));
        assert !recipe.isBlank();
        return new DeleteRecipeCommand(recipe);
    }

    /**
     * Adds the ingredients into the ingredient list.
     *
     * @param ingredientNames Array of name of ingredients
     * @param quantities      Array of quantity of ingredients
     * @param ingredients     Ingredient list to store the ingredients
     * @return null if there's no error and an IncorrectCommand if there is.
     */
    private static IncorrectCommand addIngredients(ArrayList<String> ingredientNames, ArrayList<Integer> quantities,
                                                   IngredientList ingredients) {
        if (ingredientNames.size() != quantities.size()) {
            return new IncorrectCommand("Should have same number of ingredient names and quantities");
        }
        for (int i = 0; i < ingredientNames.size(); i++) {
            Ingredient ingredient = new Ingredient(ingredientNames.get(i), quantities.get(i), null);
            assert !ingredientNames.get(i).isBlank();
            assert quantities.get(i) != null && quantities.get(i) != 0;
            try {
                ingredients.add(ingredient);
            } catch (DuplicateDataException e) {
                return new IncorrectCommand(MESSAGE_INVALID_COMMAND_FORMAT);
            }
        }
        return null;
    }

    private static Command prepareViewIngredient(String args) throws InvalidArgFormatException {
        final ArgParser argParser = new ArgParser(PREFIX_INGREDIENT);
        try {
            argParser.parse(args);
        } catch (InvalidPrefixException | MissingPrefixException | MultipleArgsException e) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewIngredientCommand.MESSAGE_USAGE));
        }

        final String ingredient = new IngredientParser().parse(argParser.getSingleArg(PREFIX_INGREDIENT));
        return new ViewIngredientCommand(ingredient);
    }

    private static Command prepareViewRecipe(String args) throws InvalidArgFormatException {
        final ArgParser argParser = new ArgParser(PREFIX_RECIPE);
        try {
            argParser.parse(args);
        } catch (InvalidPrefixException | MissingPrefixException | MultipleArgsException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewRecipeCommand.MESSAGE_USAGE));
        }

        final String recipe = new RecipeParser().parse(argParser.getSingleArg(PREFIX_RECIPE));
        return new ViewRecipeCommand(recipe);
    }
}
