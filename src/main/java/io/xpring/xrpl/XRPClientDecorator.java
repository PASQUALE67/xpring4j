package io.xpring.xrpl;

import io.xpring.xrpl.model.XRPTransaction;

import java.math.BigInteger;
import java.util.List;

/**
 * An common interface shared between XRPClient and the internal hierarchy of decorators.
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public interface XRPClientDecorator {
  /**
   * Get the balance of the specified account on the XRP Ledger.
   *
   * @param xrplAccountAddress The X-Address to retrieve the balance for.
   * @return A {@link BigInteger} with the number of drops in this account.
   * @throws XRPException If the given inputs were invalid.
   */
  BigInteger getBalance(final String xrplAccountAddress) throws XRPException;

  /**
   * Retrieve the transaction status for a Payment given transaction hash.
   * <p>
   * Note: This method will only work for Payment type transactions which do not have the tf_partial_payment attribute
   * set.
   * See: https://xrpl.org/payment.html#payment-flags
   * </p>
   *
   * @param transactionHash The hash of the transaction.
   * @return The status of the given transaction.
   * @throws XRPException If the given inputs were invalid.
   */
  public TransactionStatus getPaymentStatus(String transactionHash) throws XRPException;

  /**
   * Transact XRP between two accounts on the ledger.
   *
   * @param amount             The number of drops of XRP to send.
   * @param destinationAddress The X-Address to send the XRP to.
   * @param sourceWallet       The {@link Wallet} which holds the XRP.
   * @return A transaction hash for the payment.
   * @throws XRPException If the given inputs were invalid.
   */
  String send(
      final BigInteger amount,
      final String destinationAddress,
      final Wallet sourceWallet
  ) throws XRPException;

  /**
   * Retrieve the latest validated ledger sequence on the XRP Ledger.
   *
   * @return A long representing the sequence of the most recently validated ledger.
   * @throws XRPException If the given inputs were invalid.
   */
  int getLatestValidatedLedgerSequence() throws XRPException;

  /**
   * Retrieve the raw transaction status for the given transaction hash.
   *
   * @param transactionHash The hash of the transaction.
   * @return an {@link RawTransactionStatus} containing the raw transaction status.
   * @throws XRPException If the given inputs were invalid.
   */
  RawTransactionStatus getRawTransactionStatus(String transactionHash) throws XRPException;

  /**
   * Return the history of payments for the given account.
   * <p>
   * Note: This method only works for payment type transactions. See "https://xrpl.org/payment.html"
   * Note: This method only returns the history that is contained on the remote node,
   * which may not contain a full history of the network.
   * </p>
   * @param address The address (account) for which to retrieve payment history.
   * @return An array of transactions associated with the account.
   * @throws XRPException If there was a problem communicating with the XRP Ledger.
   */
  List<XRPTransaction> paymentHistory(String address) throws XRPException;

  /**
   * Check if an address exists on the XRP Ledger.
   *
   * @param address The address to check the existence of.
   * @return A boolean if the account is on the XRPLedger.
   * @throws XRPException If the given inputs were invalid.
   */
  boolean accountExists(String address) throws XRPException;

  /**
   * Retrieve the transaction corresponding to the given transaction hash.
   *
   * @param transactionHash The hash of the transaction to retrieve.
   * @return An XRPTransaction object representing an XRP Ledger transaction.
   * @throws XRPException If the transaction hash was invalid.
   */
  XRPTransaction getTransaction(String transactionHash) throws XRPException;
}
