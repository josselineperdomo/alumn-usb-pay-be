DROP TABLE "uphold_card";

ALTER TABLE "payment"
    ADD COLUMN "uphold_card_id" char(36);