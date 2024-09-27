-- Add lender_name column if it does not exist
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns 
        WHERE table_name = 'debit_request_details' 
        AND column_name = 'lender_name'
    ) THEN
        ALTER TABLE debit_request_details 
        ADD COLUMN lender_name VARCHAR(255);
    END IF;
END $$;

-- Add sub_merchant_id column if it does not exist
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns 
        WHERE table_name = 'debit_request_details' 
        AND column_name = 'sub_merchant_id'
    ) THEN
        ALTER TABLE debit_request_details 
        ADD COLUMN sub_merchant_id VARCHAR(255);
    END IF;
END $$;
