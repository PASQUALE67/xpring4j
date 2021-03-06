package io.xpring.xrpl.model;

import org.immutables.value.Value;
import org.xrpl.rpc.v1.Payment.PathElement;

import java.util.Optional;

/**
 * A path step in an XRP Ledger Path.
 *
 * @see "https://xrpl.org/paths.html#path-steps"
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@Value.Immutable
public interface XRPPathElement {
  static ImmutableXRPPathElement.Builder builder() {
    return ImmutableXRPPathElement.builder();
  }

  /**
   * (Optional) If present, this path step represents rippling through the specified address.
   * MUST NOT be provided if this path element specifies the currency or issuer fields.
   *
   * @return An Optional {@link String} representing the account of this {@link XRPPathElement}.
   */
  Optional<String> account();

  /**
   * (Optional) If present, this path element represents changing currencies through an order book.
   * The currency specified indicates the new currency. MUST NOT be provided if this path
   * element specifies the account field.
   *
   * @return The Optional {@link XRPCurrency} of this {@link XRPPathElement}.
   */
  Optional<XRPCurrency> currency();

  /**
   * (Optional) If present, this path element represents changing currencies and this address
   * defines the issuer of the new currency. If omitted in a path element with a non-XRP currency,
   * a previous element of the path defines the issuer. If present when currency is omitted,
   * indicates a path element that uses an order book between same-named currencies with different issuers.
   * MUST be omitted if the currency is XRP. MUST NOT be provided if this element specifies the account field.
   *
   * @return An Optional {@link String} representing the issuer of a new currency.
   */
  Optional<String> issuer();

  /**
   * Constructs an {@link XRPPathElement} from a {@link org.xrpl.rpc.v1.Payment.PathElement}.
   *
   * @param pathElement a {@link org.xrpl.rpc.v1.Payment.PathElement} (protobuf object) whose field values will be used
   *                    to construct an {@link XRPPathElement}
   * @return an {@link XRPPathElement} with its fields set via the analogous protobuf fields.
   * @see <a href="https://github.com/ripple/rippled/blob/develop/src/ripple/proto/org/xrpl/rpc/v1/transaction.proto#L227">
   * PathElement protocol buffer</a>
   */
  static XRPPathElement from(PathElement pathElement) {
    Optional<String> account = Optional.empty();
    if (pathElement.hasAccount()) {
      account = Optional.ofNullable(pathElement.getAccount().getAddress().isEmpty()
              ? null : pathElement.getAccount().getAddress());
    }

    Optional<XRPCurrency> currency = Optional.empty();
    if (pathElement.hasCurrency()) {
      currency = Optional.ofNullable(XRPCurrency.from(pathElement.getCurrency()));
    }

    Optional<String> issuer = Optional.empty();
    if (pathElement.hasIssuer()) {
      issuer = Optional.ofNullable(pathElement.getIssuer().getAddress().isEmpty()
              ? null : pathElement.getIssuer().getAddress());
    }

    return builder()
        .account(account)
        .currency(currency)
        .issuer(issuer)
        .build();
  }
}
