package com.ef;


import com.ef.common.CommandLineArguments;
import com.ef.common.Duration;
import com.ef.common.ParserException;
import com.ef.common.Utils;
import com.ef.config.MySQLPersistModule;
import com.ef.config.ParserModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * Note: The name <code>Parser</code> was infered as a restriction,
 * otherwise it would be named <code>WallethubParserApp</code>
 */
public class Parser {
    public static void main(String[] args) {
        try {
            Injector injector = Guice.createInjector(
                    new ParserModule(), new MySQLPersistModule()
            );

            CommandLineArguments arguments = parseArgs(args);


        } catch (ParserException ex) {
            System.out.println(ex.getMessage());

            System.exit(1);
        }
    }

    static CommandLineArguments parseArgs(String... args) {
        String[] paramsArgs = Arrays.copyOfRange(args, 1, args.length);

        Map<String, String> paramsMap = Utils.parseArgsIntoMap(paramsArgs);

        final Date startDate = Optional.ofNullable(paramsMap.get("startDate"))
                .flatMap(Utils::parseRequestLogDate)
                .orElseThrow(() -> new ParserException(
                        "The argument startDate is missing or invalid. The format is " +
                                "yyyy-MM-dd.HH:mm:ss, e.g. 2017-01-01.13:00:00."
                ));

        final Duration duration = Optional.ofNullable(paramsMap.get("duration"))
                .flatMap(Duration::parseFromParamName)
                .orElseThrow(() -> new ParserException(
                        "The argument duration is missing or invalid. The possible values are " +
                                "hourly or daily."
                ));

        final Integer threshold = Optional.ofNullable(paramsMap.get("threshold"))
                .filter(text -> text.matches("\\d+"))
                .map(Integer::parseInt)
                .filter(val -> val > 0)
                .orElseThrow(() -> new ParserException(
                        "The argument threshold is missing or invalid. Specify a natural value" +
                                " no less than 0."
                ));

        return new CommandLineArguments(startDate, duration, threshold);
    }
}
