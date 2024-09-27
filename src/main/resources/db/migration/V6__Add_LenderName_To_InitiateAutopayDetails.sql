-- Add lender_name column to initiate_autopay_details if it doesn't exist
ALTER TABLE initiate_autopay_details
ADD COLUMN IF NOT EXISTS lender_name VARCHAR(255);
