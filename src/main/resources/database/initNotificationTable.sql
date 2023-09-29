CREATE TABLE IF NOT EXISTS notification_table
(
    id              bigint          GENERATED BY DEFAULT AS IDENTITY,
    date            timestamp(6)    NOT NULL,
    is_read         boolean         DEFAULT false,
    message         varchar         NOT NULL,
    type            smallint        NOT NULL,
    user_id         bigint,
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES usr(id) ON DELETE CASCADE
);