CREATE TABLE "uphold_card" (
  "uphold_card_id" varchar PRIMARY KEY,
  "balance" float,
  "available" float,
  "currency" varchar NOT NULL,
  "name" varchar
);

CREATE TABLE "uphold_credentials" (
  "credentials_id" smallint PRIMARY KEY,
  "access_token" varchar NOT NULL,
  "refresh_token" varchar NOT NULL,
  "created_at" timestamp NOT NULL DEFAULT now(),
  "updated_at" timestamp
);

ALTER TABLE "uphold_credentials" ADD CONSTRAINT uphold_credentials_uk UNIQUE ("access_token", "refresh_token");
