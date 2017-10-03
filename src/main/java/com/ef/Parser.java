package com.ef;


import com.ef.common.QueryArguments;
import com.ef.common.Duration;
import com.ef.common.ParserException;
import com.ef.common.Utils;
import com.ef.config.MySQLPersistModule;
import com.ef.config.ParserModule;
import com.ef.domain.service.RequestLogService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * Note: The name <code>Parser</code> was infered as a restriction,
 * otherwise it would be named <code>WallethubParserApp</code>
 */
@Singleton
public class Parser {

    public static void main(String[] args) {
        try {
            Injector injector = Guice.createInjector(
                    new ParserModule(), new MySQLPersistModule()
            );

            //Initialize persistence
            PersistService instance = injector.getInstance(PersistService.class);
            instance.start();

            final RequestLogService requestLogService =
                    injector.getInstance(RequestLogService.class);

            parseLogFileArg(args)
                    .ifPresent(requestLogService::parseLogFileIntoDatabase);

            parseQueryArgs(args)
                    .ifPresent(requestLogService::printRequestLogs);
        } catch (ParserException ex) {
            System.out.println(ex.getMessage());

            System.exit(1);
        }
    }

    /**
     * If any of the query arguments is specified then it returns
     * a valid {@link QueryArguments} instance. If any of the parameters
     * is missing or invalid a {@link ParserException} is thrown.
     *
     * @param args application arguments
     * @return An {@link Optional} with the {@link File} instance to the log file
     */
    static Optional<QueryArguments> parseQueryArgs(String... args) {
        Map<String, String> paramsMap = Utils.parseArgsIntoMap(args);

        if (!(paramsMap.containsKey("startDate")
                || paramsMap.containsKey("duration")
                || paramsMap.containsKey("threshold")
        )) {
            return Optional.empty();
        }

        final Date startDate = Optional.of(paramsMap.getOrDefault("startDate", ""))
                .filter(text -> !text.isEmpty())
                .flatMap(Utils::parseRequestLogDate)
                .orElseThrow(() -> new ParserException(
                        "The argument startDate is missing or invalid. The format is " +
                                "yyyy-MM-dd.HH:mm:ss, e.g. 2017-01-01.13:00:00."
                ));

        final Duration duration = Optional.of(paramsMap.getOrDefault("duration", ""))
                .filter(text -> !text.isEmpty())
                .flatMap(Duration::parseFromParamName)
                .orElseThrow(() -> new ParserException(
                        "The argument duration is missing or invalid. The possible values are " +
                                "hourly or daily."
                ));

        final Integer threshold = Optional.of(paramsMap.getOrDefault("threshold", ""))
                .filter(text -> !text.isEmpty())
                .filter(text -> text.matches("\\d+"))
                .map(Integer::parseInt)
                .filter(val -> val > 0)
                .orElseThrow(() -> new ParserException(
                        "The argument threshold is missing or invalid. Specify a natural value" +
                                " no less than 0."
                ));

        return Optional.of(new QueryArguments(startDate, duration, threshold));
    }


    /**
     * If the parseLogFile is specified then it returns a valid path of
     * the argument. If the path of the log file is wrong a {@link ParserException}
     * is thrown
     *
     * @param args application arguments
     * @return An {@link Optional} with the {@link File} instance to the log file
     */
    static Optional<File> parseLogFileArg(String... args) {
        Map<String, String> paramsMap = Utils.parseArgsIntoMap(args);

        if (!paramsMap.containsKey("logfile")) {
            return Optional.empty();
        }

        return Optional.of(paramsMap.get("logfile"))
                .map(File::new)
                .filter(File::canRead)
                .map(Optional::of)
                .orElseThrow(() -> new ParserException(
                        "The specified log file cannot be read"
                ));
    }
}
