ALTER TABLE "payment"
    ADD COLUMN "status" varchar(16) NOT NULL DEFAULT 'CREATED';

ALTER TABLE "payment_list_beneficiary"
    ADD COLUMN "status" varchar(16) NOT NULL DEFAULT 'CREATED';